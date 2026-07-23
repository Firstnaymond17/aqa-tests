package com.remizov.aqa.tests;

import com.remizov.aqa.config.TestConfig;
import com.remizov.aqa.config.TokenGenerator;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.equalTo;

class LoginTests extends BaseTest {

    @Test
    void loginSuccess_whenExternalAuthReturnsOk() {
        String token = TokenGenerator.validToken();

        stubFor(post(urlEqualTo(TestConfig.EXTERNAL_AUTH_PATH))
                .willReturn(aResponse().withStatus(200)));

        baseRequest()
                .formParam("token", token)
                .formParam("action", TestConfig.ACTION_LOGIN)
                .when().post(TestConfig.ENDPOINT_PATH)
                .then()
                .statusCode(200)
                .body("result", equalTo("OK"));
    }
}