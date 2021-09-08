package com.jrx.cloud.user.api.config;

import com.jrx.cloud.assembly.rsa.RsaKeyPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author x
 * @version 1.0  2021/7/28
 */
@Slf4j
@Configuration
public class RsaConfig {

    @Bean
    public RsaKeyPair rsaKeyPair() {
        var rsaKeyPair = new RsaKeyPair();
        try {
            // 读取公钥信息
            var publicKeyStr = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("pub.key").toURI())));
            publicKeyStr = publicKeyStr
                    .replace("\\n", "")
                    .replace("\\r", "")
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");

            var keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
            rsaKeyPair.setPublicKey(KeyFactory.getInstance("RSA").generatePublic(keySpec));
        } catch (Exception e) {
            log.error("### [init] Fail to read rsa public key : {} ###", e.getMessage(), e);
        }

        try {
            // 读取密钥信息
            var privateKeyStr= new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("pri.key").toURI())));
            privateKeyStr = privateKeyStr
                    .replace("\\n", "")
                    .replace("\\r", "")
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "");

            var keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
            rsaKeyPair.setPrivateKey(KeyFactory.getInstance("RSA").generatePrivate(keySpec));
        } catch (Exception e) {
            log.error("### [init] Fail to read rsa private key : {} ###", e.getMessage(), e);
        }

        return rsaKeyPair;
    }
}
