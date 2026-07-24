package com.remizov.aqa.tests;

import com.remizov.aqa.config.TestConfig;
import com.remizov.aqa.config.TokenGenerator;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.equalTo;

class ActionTests extends BaseTest {

    private void loginToken(String token) {
        stubFor(post(urlEqualTo(TestConfig.EXTERNAL_AUTH_PATH))
                .willReturn(aResponse().withStatus(200)));

        baseRequest()
                .formParam("token", token)
                .formParam("action", TestConfig.ACTION_LOGIN)
                .when().post(TestConfig.ENDPOINT_PATH)
                .then().statusCode(200);
    }

    @Test
    void actionSucceeds_forLoggedInToken() {
        String token = TokenGenerator.validToken();
        loginToken(token);

        stubFor(post(urlEqualTo(TestConfig.EXTERNAL_DO_ACTION_PATH))
                .willReturn(aResponse().withStatus(200)));

        baseRequest()
                .formParam("token", token)
                .formParam("action", TestConfig.ACTION_ACTION)
                .when().post(TestConfig.ENDPOINT_PATH)
                .then()
                .statusCode(200)
                .body("result", equalTo("OK"));

        verify(postRequestedFor(urlEqualTo(TestConfig.EXTERNAL_DO_ACTION_PATH)));
    }

    @Test
    void actionFails_forTokenWithoutLogin() {
        String token = TokenGenerator.validToken();

        baseRequest()
                .formParam("token", token)
                .formParam("action", TestConfig.ACTION_ACTION)
                .when().post(TestConfig.ENDPOINT_PATH)
                .then()
                .statusCode(403)
                .body("result", equalTo("ERROR"));

        verify(0, postRequestedFor(urlEqualTo(TestConfig.EXTERNAL_DO_ACTION_PATH)));
    }
}