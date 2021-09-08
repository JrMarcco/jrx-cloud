package com.jrx.cloud.assembly.rsa;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Key;

/**
 * @author x
 * @version 1.0  2021/7/28
 */
@Data
@NoArgsConstructor
public class RsaKeyPair {
    private Key publicKey;
    private Key privateKey;
}
