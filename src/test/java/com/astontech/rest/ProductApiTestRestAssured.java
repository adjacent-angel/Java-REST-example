package com.astontech.rest;


import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ProductApiTestRestAssured {

    @Test
    public void testEndpointShouldReturn200() {
        // not really a test, just validates setup
        get("/test")
                .then()
                .statusCode(200);
    }

    @Test
    public void whenUsePathParmaValidId_thenOK() {
        given().pathParam("id", 1)
                .when().get("/product/{id}")
                .then().statusCode(200);
    }

    @Test
    public void WhenUsePathParamInvalidId_thenNOT_FOUND() {
        given().pathParam("id", 9999)
                .when().get("/product/{id}")
                .then().statusCode(404);
    }

    @Test
    public void whenUseQueryParmaValidSku_thenOK() {
        given().queryParam("sku", "TV-SAM-284571")
                .when().get("/product")
                .then().statusCode(200);
    }

    @Test
    public void whenFindBySkuAssertProductDescription() {
        given().queryParam("sku", "TV-SAM-283601")
                .when().get("/product")
                .then().statusCode(200)
                .assertThat()
                .body("description", equalTo("Samsung 60\" TV"));
    }

    @Test
    public void getResponseTime() {
        System.out.println("Response time: " + get("/product/").timeIn(TimeUnit.MILLISECONDS)+ "ms");
    }

    @Test
    public void getResponseContentType() {
        System.out.println("Content Type of response: " + get("/product/").then().extract().contentType());
    }

    @Test
    public void saveProductShouldReturnId() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("sku", "A17-223");
        requestBody.put("description", "Apple IPad");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .post("/product/")
                .then().statusCode(201)
                .body("$", hasKey("id"))
                .body("sku", equalTo("A17-223"))
                .body("description", equalTo("Apple IPad"));
    }

    @Test
    public void patchProductWithInvalidIdShouldThrowException(){
        JSONObject requestBody = new JSONObject();
        requestBody.put("sku", "A17-223");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 99999)
                .when()
                .patch("/product/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void patchProductWithInvalidFieldNameShouldThrowException(){
        JSONObject requestBody = new JSONObject();
        requestBody.put("dog", "A17-223");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .pathParam("id", 1)
                .when()
                .patch("/product/{id}")
                .then()
                .statusCode(422);

    }

    @Test
    public void patchProductWithValidFieldsShouldUpdateResource() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("quantity", 72);
        requestBody.put("price", 295.99);
        requestBody.put("weight", 49.0);
        requestBody.put("description", "23x2x60");

        given().header("Content-Type", "application/json")
                .body(requestBody.toJSONString())
                .patch("/product/1")
                .then()
                .statusCode(202)
                .assertThat()
                .body("quantity", equalTo(72))
                .body("price", equalTo(295.99f))
                .body("weight", equalTo(49.0f))
                .body("description", equalTo("23x2x60"));
    }
}
