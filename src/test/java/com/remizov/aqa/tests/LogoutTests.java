package com.remizov.aqa.tests;

import com.remizov.aqa.config.TestConfig;
import com.remizov.aqa.config.TokenGenerator;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.equalTo;

class LogoutTests extends BaseTest {

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
    void logoutSucceeds_forLoggedInToken() {
        String token = TokenGenerator.validToken();
        loginToken(token);

        baseRequest()
                .formParam("token", token)
                .formParam("action", TestConfig.ACTION_LOGOUT)
                .when().post(TestConfig.ENDPOINT_PATH)
                .then()
                .statusCode(200)
                .body("result", equalTo("OK"));
    }

    @Test
    void tokenCannotPerformAction_afterLogout() {
        String token = TokenGenerator.validToken();
        loginToken(token);

        baseRequest()
                .formParam("token", token)
                .formParam("action", TestConfig.ACTION_LOGOUT)
                .when().post(TestConfig.ENDPOINT_PATH)
                .then().statusCode(200);

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