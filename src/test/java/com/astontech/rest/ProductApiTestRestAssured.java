package com.astontech.rest;


import org.junit.jupiter.api.Test;
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
}
