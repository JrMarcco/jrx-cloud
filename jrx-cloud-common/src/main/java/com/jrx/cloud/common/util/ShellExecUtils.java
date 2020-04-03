package com.jrx.cloud.common.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hongjc
 * @version 1.0  2020/4/3
 */
@Slf4j
public class ShellExecUtils {

    private static ThreadPoolExecutor executor;

    static {
        var namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("shell-exec-msg-pool-%d").build();

        executor = new ThreadPoolExecutor(8, 16, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(128),
                namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 移除文件。
     *
     * @param filePath 临时目录路径
     */
    public static void removeFile(String filePath) throws IOException, InterruptedException {
        execShell(String.format("rm %s -f", filePath));
    }

    /**
     * 移除目录。
     *
     * @param dirPath 临时目录路径
     */
    public static void removeDir(String dirPath) throws IOException, InterruptedException {
        execShell(String.format("rm %s -rf", dirPath));
    }

    /**
     * 压缩文件夹。
     *
     * @param dirPath 目标文件夹路径
     */
    public static String zipDirectory(String dirPath) throws IOException, InterruptedException {
        execShell(String.format("zip -r %s.zip %s", dirPath, dirPath));
        return dirPath + ".zip";
    }

    /**
     * 执行sh文件。
     *
     * @param shPath sh文件路径
     * @param params 参数（可选）
     */
    public static void execShFile(String shPath, List<String> params) throws IOException, InterruptedException {
        var command = new StringBuffer("sh ").append(shPath);
        if (CollectionUtils.isEmpty(params)) {
            params.forEach(param -> command.append(" ").append(param));
        }
        execShell(command.toString());
    }

    public static void execShell(String command) throws IOException, InterruptedException {
        try {
            var process = Runtime.getRuntime().exec(command);
            log.info("### Execute shell command: {} ###", command);

            startMsgReadThread(process);
            var returnStatus = process.waitFor();
            if (returnStatus != 0) {
                log.error("### Failed to execute shell command, the return status is {} ###", returnStatus);
            }
        } catch (IOException | InterruptedException e) {
            log.error("### Execute shell command error，command:{} ###", command, e);
            throw e;
        }
    }

    // ----------------------------------------< Private Method >----------------------------------------
    private static void startMsgReadThread(Process process) {
        executor.submit(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    log.info("### {} ###", line);
                }
            } catch (IOException e) {
                log.error("### Read shell output msg error: {} ###", e.getMessage(), e);
            }
        });

        executor.submit(() -> {
            try (BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = err.readLine()) != null) {
                    log.info("### {} ###", line);
                }
            } catch (IOException e) {
                log.error("### Read shell error msg error: {} ###", e.getMessage(), e);
            }
        });
    }
}
