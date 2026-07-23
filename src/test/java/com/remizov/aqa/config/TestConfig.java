package com.remizov.aqa.config;

public final class TestConfig {

    public static final String APP_BASE_URL = "http://localhost:8080";
    public static final String ENDPOINT_PATH = "/endpoint";

    public static final int MOCK_PORT = 8888;

    public static final String VALID_API_KEY = "qazWSXedc";
    public static final String INVALID_API_KEY = "invalidKey123";

    public static final String ACTION_LOGIN = "LOGIN";
    public static final String ACTION_ACTION = "ACTION";
    public static final String ACTION_LOGOUT = "LOGOUT";

    public static final String EXTERNAL_AUTH_PATH = "/auth";
    public static final String EXTERNAL_DO_ACTION_PATH = "/doAction";

    private TestConfig() {
    }
}