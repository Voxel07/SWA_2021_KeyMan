package de.hse.swa.jaxquarkus.step1;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class Step1Test {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/step1")
          .then()
             .statusCode(200)
             .body(is("hello from step1"));
    }

}