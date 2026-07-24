package com.remizov.aqa.tests;

import com.remizov.aqa.config.TestConfig;
import com.remizov.aqa.config.TokenGenerator;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.equalTo;

class NegativeTests extends BaseTest {

    @Test
    void requestWithoutApiKey_isRejected() {
        RestAssured.given()
                .header("Accept", "application/json")
                .contentType("application/x-www-form-urlencoded")
                .formParam("token", TokenGenerator.validToken())
                .formParam("action", TestConfig.ACTION_LOGIN)
                .when().post(TestConfig.ENDPOINT_PATH)
                .then()
                .statusCode(401)
                .body("result", equalTo("ERROR"));
    }

    @Test
    void requestWithWrongApiKey_isRejected() {
        RestAssured.given()
                .header("X-Api-Key", TestConfig.INVALID_API_KEY)
                .header("Accept", "application/json")
                .contentType("application/x-www-form-urlencoded")
                .formParam("token", TokenGenerator.validToken())
                .formParam("action", TestConfig.ACTION_LOGIN)
                .when().post(TestConfig.ENDPOINT_PATH)
                .then()
                .statusCode(401)
                .body("result", equalTo("ERROR"));
    }

    @Test
    void tooShortToken_returnsError() {
        baseRequest()
                .formParam("token", TokenGenerator.tooShortToken())
                .formParam("action", TestConfig.ACTION_LOGIN)
                .when().post(TestConfig.ENDPOINT_PATH)
                .then()
                .statusCode(400)
                .body("result", equalTo("ERROR"));

        verify(0, postRequestedFor(urlEqualTo(TestConfig.EXTERNAL_AUTH_PATH)));
    }

    @Test
    void invalidCharsToken_returnsError() {
        baseRequest()
                .formParam("token", TokenGenerator.invalidCharsToken())
                .formParam("action", TestConfig.ACTION_LOGIN)
                .when().post(TestConfig.ENDPOINT_PATH)
                .then()
                .statusCode(400)
                .body("result", equalTo("ERROR"));

        verify(0, postRequestedFor(urlEqualTo(TestConfig.EXTERNAL_AUTH_PATH)));
    }
}