package com.jrx.cloud.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author x
 * @version 1.0  2021/7/29
 */
@Slf4j
public class RsaUtils {

    private static final Map<String, Key> keyPair = new HashMap<>(2);

    /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小（秘钥位数为1024时最大解密大小128，位数为2048时最大解密大小256）
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    static {
        loadKeyPair();
    }

    /**
     * 加载公钥 / 秘钥对
     */
    private static void loadKeyPair() {
        try {
            var classPathResource = new ClassPathResource("public_key.pem");
            try (var inputStream = classPathResource.getInputStream()) {
                var publicKeyStr = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).replaceAll("[\\n\\r]", "");

                var keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
                keyPair.put(PUBLIC_KEY, KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(keySpec));
            }
        } catch (Exception e) {
            log.error("### [Load] Fail to load public key: {} ###", e.getMessage(), e);
        }

        try {
            var classPathResource = new ClassPathResource("private_key.pem");
            try (var inputStream = classPathResource.getInputStream()) {
                var privateKeyStr = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).replaceAll("[\\n\\r]", "");

                var keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
                keyPair.put(PRIVATE_KEY, KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(keySpec));
            }
        } catch (Exception e) {
            log.error("### [Load] Fail to load private Key: {} ###", e.getMessage(), e);
        }
    }

    public static String getPublicKey() {
        return Base64.getEncoder().encodeToString(keyPair.get(PUBLIC_KEY).getEncoded());
    }

    public static String getPrivateKey() {
        return Base64.getEncoder().encodeToString(keyPair.get(PRIVATE_KEY).getEncoded());
    }

    /**
     * 通过秘钥加密数据。
     *
     * @param content 加密内容
     */
    public static String encryptByPrivateKey(String content) {
        try {
            var keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            var privateK = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(getPrivateKey())));
            var cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateK);

            var data = content.getBytes(StandardCharsets.UTF_8);

            try (var out = new ByteArrayOutputStream()) {
                for (var i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
                    var doFinal = cipher.doFinal(Arrays.copyOfRange(data, i, i + MAX_ENCRYPT_BLOCK));
                    out.write(doFinal, 0, doFinal.length);
                }
                return Base64.getEncoder().encodeToString(out.toByteArray());
            }

        } catch (Exception e) {
            log.error("### [Encrypt] Fail to encrypt content by ras private key: {} ###", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 通过公钥解密
     */
    public static String decryptByPublicKey(String encryptedData) {
        try {
            var keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            var publicK = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(getPublicKey())));
            var cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicK);

            var data = Base64.getDecoder().decode(encryptedData);
            try (var out = new ByteArrayOutputStream()) {
                for (var i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
                    var doFinal = cipher.doFinal(Arrays.copyOfRange(data, i, i + MAX_DECRYPT_BLOCK));
                    out.write(doFinal, 0, doFinal.length);
                }
                return out.toString();
            }
        } catch (Exception e) {
            log.error("### [Decrypt] Fail to decrypt data by ras public key: {} ###", e.getMessage(), e);
            return null;
        }
    }
}
