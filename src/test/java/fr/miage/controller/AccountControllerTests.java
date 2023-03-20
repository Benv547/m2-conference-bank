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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTests {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setupContext() {
        RestAssured.port = port;
    }

    @Test
    void postAndgetAccount_isTheSame() throws Exception {
        AccountInput a1 = new AccountInput("John", "Doe", "john@doe.fr");
        Response response = given().body(toJsonString(a1)).contentType(ContentType.JSON).when().post("/account").then().statusCode(HttpStatus.SC_OK).extract().response();
        String id = response.getBody().asString();
        Response response2 = given().body(toJsonString(a1)).contentType(ContentType.JSON).when().get("/account").then().statusCode(HttpStatus.SC_OK).extract().response();
        String id2 = response2.getBody().asString();
        assertEquals(id, id2);
    }

    private String toJsonString(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r);
    }
}
