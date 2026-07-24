package com.remizov.aqa.tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.remizov.aqa.config.TestConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public abstract class BaseTest {

    protected static WireMockServer wireMockServer;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(TestConfig.MOCK_PORT));
        wireMockServer.start();
        configureFor("localhost", TestConfig.MOCK_PORT);

        RestAssured.baseURI = TestConfig.APP_BASE_URL;
        RestAssured.filters(new AllureRestAssured());
    }

    @AfterAll
    static void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @BeforeEach
    void resetStubs() {
        WireMock.reset();
    }

    protected RequestSpecification baseRequest() {
        return RestAssured.given()
                .header("X-Api-Key", TestConfig.VALID_API_KEY)
                .header("Accept", "application/json")
                .contentType("application/x-www-form-urlencoded");
    }
}