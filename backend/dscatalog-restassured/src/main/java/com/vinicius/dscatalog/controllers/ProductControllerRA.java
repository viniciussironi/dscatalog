package com.vinicius.dscatalog.controllers;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import ch.qos.logback.core.net.server.Client;
import com.vinicius.dscatalog.utils.TokenUtil;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductControllerRA {

    private String adminUsername, adminPassword;
    private String adminToken, invalidToken;
    private Long existingId, nonExistingId;
    private Map<String, Object> postProductInstance;

    @BeforeEach
    void setUp() {
        baseURI = "http://localhost:8080";

        adminUsername = "maria@gmail.com";
        adminPassword = "123456";

        adminToken = TokenUtil.obtainAcessToken(adminUsername, adminPassword);
        invalidToken = adminToken + "abcdefg";

        postProductInstance = new HashMap<>();
        postProductInstance.put("name", "Meu Produto");
        postProductInstance.put("price", 99.99);
        postProductInstance.put("description", "Descrição");
        postProductInstance.put("imgUrl", "Link");
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() {
        existingId = 2L;
        given()
                .get("/products/{id}", existingId)
        .then()
                .statusCode(200)
                .body("id", is(2))
                .body("name", equalTo("Smart TV"))
                .body("imgUrl", equalTo("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg"))
                .body("price", is(2190.0F));
    }

    @Test
    public void findAllShouldReturnPage() {
        existingId = 2L;
        given()
            .get("/products")
        .then()
            .statusCode(200)
            .body("content.name", hasItem("Macbook Pro"))
            .body("content.name", hasItem("PC Gamer Tera"));
    }

    @Test
    public void findAllShouldReturnPageWhenSearchByName() {
        existingId = 2L;
        given()
            .get("/products?name={productName}", "Macbook")
        .then()
            .statusCode(200)
            .body("content[0].id", is(3))
            .body("content[0].name", equalTo("Macbook Pro"));
    }

    @Test
    public void findAllShouldReturnPageWhenPriceGreaterThan2000() {
        existingId = 2L;
        given()
                .get("/products?size=25")
        .then()
                .statusCode(200)
                .body("content.findAll {it.price > 2000}.name", hasItems("Smart TV", "PC Gamer Weed"));

    }

    @Test
    public void insertShouldReturnProductCreatedWhenAdminLogged() {
        JSONObject newProduct = new JSONObject(postProductInstance);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post("/products")
        .then()
                .statusCode(201)
                .body("name", equalTo("Meu Produto"));

    }

    @Test
    public void insertShouldReturnProductCreatedWhenInvalidToken() {
        JSONObject newProduct = new JSONObject(postProductInstance);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + invalidToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(401);
    }

    @Test
    public void deleteShouldNoContentWhenValidToken() {
        Long existingId = 3L;

        given()
                .header("Authorization", "Bearer " + adminToken)
        .when()
                .delete("/products/{id}", existingId)
        .then()
                .statusCode(204);
    }

    @Test
    public void deleteShouldNoContentWhenInvalidToken() {
        Long existingId = 6L;

        given()
                .header("Authorization", "Bearer " + invalidToken)
                .when()
                .delete("/products/{id}", existingId)
                .then()
                .statusCode(401);
    }

    @Test
    public void findByIdShouldReturnProductWhenInvalidToken() {
        Long existingId = 10L;

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get("/products/{id}", existingId)
                .then()
                .statusCode(200);
    }

    @Test
    public void findByIdShouldReturnProductWhenValidToken() {
        Long existingId = 10L;

        given()
                .header("Authorization", "Bearer " + invalidToken)
                .when()
                .get("/products/{id}", existingId)
                .then()
                .statusCode(401);
    }

    @Test
    public void findByIdShouldReturnProductWhenNonExistsId() {
        Long existingId = 100L;

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get("/products/{id}", existingId)
                .then()
                .statusCode(404);
    }
}
