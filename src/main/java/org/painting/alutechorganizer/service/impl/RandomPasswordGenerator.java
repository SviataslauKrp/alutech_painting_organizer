package org.painting.alutechorganizer.service.impl;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomPasswordGenerator {
    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        String randomString = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        return randomString.substring(0, length);
    }
}
