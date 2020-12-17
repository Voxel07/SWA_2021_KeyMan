package de.hse.swa.jaxquarkus.step2;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class Step2Test {

    @Test
    public void testStep2Endpoint() {
    	String name = "Chrissi";
        given()
          .pathParam("Name", name)
          .when()
             .get("/step2/greeting/{Name}")
          .then()
             .statusCode(200)
             .body(is("hello " + name));
    }

}