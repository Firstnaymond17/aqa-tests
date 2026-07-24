package com.remizov.aqa.config;

import java.security.SecureRandom;

public final class TokenGenerator {

    private static final String ALPHABET = "0123456789ABCDEF";
    private static final SecureRandom RANDOM = new SecureRandom();

    private TokenGenerator() {
    }

    public static String validToken() {
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public static String tooShortToken() {
        return validToken().substring(0, 10);
    }

    public static String invalidCharsToken() {
        return "GHIJKLMNOPQRSTUVWXYZGHIJKLMNOPQR";
    }
}