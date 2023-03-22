package fr.miage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.miage.bank.account.entity.AccountInput;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTests {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setupContext() {
        RestAssured.port = port;
    }

    @Test
    void postAccount_ExpectTrue() throws Exception {
        AccountInput a1 = new AccountInput("John", "Doe", "john@doe.fr");
        Response response = given().body(toJsonString(a1)).contentType(ContentType.JSON).when().post("/account").then().statusCode(HttpStatus.SC_CREATED).extract().response();
        String json = response.getBody().asString();

        assertTrue(json.contains("John"));
        assertTrue(json.contains("Doe"));
        assertTrue(json.contains("john@doe.fr"));
    }

    private String toJsonString(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r);
    }
}
