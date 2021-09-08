package com.jrx.cloud.common.util;

import com.jrx.cloud.assembly.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
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
     * RSA最大加密明文大小（秘钥位数为1024时最大解密大小117，位数为2048时最大解密大小245）
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小（秘钥位数为1024时最大解密大小128，位数为2048时最大解密大小256）
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    private static final String BLANK = "\\u0000";


    static {
        loadKeyPair();
    }

    private RsaUtils() {
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

    private static PublicKey getPublicKey() {
        return (PublicKey) keyPair.get(PUBLIC_KEY);
    }

    private static PrivateKey getPrivateKey() {
        return (PrivateKey) keyPair.get(PRIVATE_KEY);
    }

    private static String getEncodedPublicKey() {
        return Base64.getEncoder().encodeToString(getPublicKey().getEncoded());
    }

    private static String getEncodedPrivateKey() {
        return Base64.getEncoder().encodeToString(getPrivateKey().getEncoded());
    }

    private static Cipher getCipher(KeyFactory keyFactory, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            var cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("### [Cipher] Fail to get cipher: {} ###", e.getMessage(), e);
            throw e;
        }
    }

    private static String doEncrypt(String content, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException, IOException {
        var data = content.getBytes(StandardCharsets.UTF_8);

        try (var out = new ByteArrayOutputStream()) {
            for (var i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
                var doFinal = cipher.doFinal(Arrays.copyOfRange(data, i, i + MAX_ENCRYPT_BLOCK));
                out.write(doFinal, 0, doFinal.length);
            }
            return Base64.getEncoder().encodeToString(out.toByteArray()).replace(BLANK, "");
        } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("### [Encrypt] Fail to do encrypt: {} ###", e.getMessage(), e);
            throw e;
        }
    }

    private static String doDecrypt(String encryptedData, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException, IOException {
        var data = Base64.getDecoder().decode(encryptedData);
        try (var out = new ByteArrayOutputStream()) {
            for (var i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
                var doFinal = cipher.doFinal(Arrays.copyOfRange(data, i, i + MAX_DECRYPT_BLOCK));
                out.write(doFinal, 0, doFinal.length);
            }
            return out.toString().replace(BLANK, "");
        } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("### [Decrypt] Fail to do decrypt: {} ###", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 通过秘钥加密数据。
     *
     * @param content 加密内容
     */
    public static String encryptByPrivateKey(String content) {
        try {
            var keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            return doEncrypt(
                    content,
                    getCipher(keyFactory, keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(getEncodedPrivateKey()))))
            );
        } catch (Exception e) {
            log.error("### [Encrypt] Fail to encrypt content by ras private key: {} ###", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 通过公钥解密
     */
    public static String decryptByPublicKey(String encryptedData) {
        try {
            var keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            return doDecrypt(
                    encryptedData,
                    getCipher(keyFactory, keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(getEncodedPublicKey()))))
            );
        } catch (Exception e) {
            log.error("### [Decrypt] Fail to decrypt data by ras public key: {} ###", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 私钥签名
     */
    public static String signByPrivateKey(String encryptData) {
        try {
            var signature = Signature.getInstance(KEY_ALGORITHM);
            signature.initSign(getPrivateKey());
            signature.update(encryptData.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            log.error("### [Sign] Fail to sign by ras private key: {} ###", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 公钥验签
     */
    public static boolean verifyByPublicKey(String encryptedSign, String sign) throws BusinessException {
        try {
            var signature = Signature.getInstance(KEY_ALGORITHM);
            signature.initVerify(getPublicKey());
            signature.update(encryptedSign.getBytes());

            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (NoSuchAlgorithmException e) {
            log.error("### [Verify] No such algorithm called {} : {} ###", KEY_ALGORITHM, e.getMessage(), e);
            throw new BusinessException(String.format("No such algorithm called %s", KEY_ALGORITHM));
        } catch (InvalidKeyException e) {
            log.error("### [Verify] Invalidate public key ###");
            throw new BusinessException("Invalidate public key.");
        } catch (SignatureException e) {
            log.error("### [Verify] Fail to verify sign ###");
            throw new BusinessException("Fail to verify sign.");
        }
    }
}
