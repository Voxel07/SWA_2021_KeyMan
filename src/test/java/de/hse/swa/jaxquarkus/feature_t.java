package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.FeatureOrm;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class feature_t{
    private static Feature FA = new Feature("1");
	private static Feature FB = new Feature("2");
	private static Feature FC = new Feature("3");
    private static Feature FD = new Feature("4");
    
    private static	Contract contractC = new Contract("3.3.2020", "3.3.2021", "ver1", "5678");
	 
	@Inject
    FeatureOrm featureOrm;
    
	@Test
	@Order(1)
	public void addFeatureToContract(){
		
        given()
        .pathParam("id", 1l)
        .contentType(MediaType.APPLICATION_JSON)
        .body(FA)//10
        .when()
        .put("feature/{id}")	
        .then()
        .statusCode(200).body(is("Feature added"));

    }
	
	@Test
	@Order(2)
	public void addFeatureToContractDuplicate(){
		
        given()
        .pathParam("id", 1l)
        .contentType(MediaType.APPLICATION_JSON)
        .body(FA)//nix
        .when()
        .put("feature/{id}")	
        .then()
        .statusCode(200).body(is("Doppelte Nr entdeckt bei Contract: 1"));

    }
	
	@Test
	@Order(3)
	public void addFeatureToContract2(){

		 given()
		 .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FB)//11
	        .when()
	        .put("feature/{id}")	
	        .then()
            .statusCode(200).body(is("Feature added"));
            
		 given()
		 .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FC)//12
	        .when()
	        .put("feature/{id}")	
	        .then()
	        .statusCode(200).body(is("Feature added"));

		}
	@Test
	@Order(4)
	public void addFeatureToContractToMannyNumbers(){
		
		 given()
		    .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FD)
	        .when()
	        .put("feature/{id}")	
	        .then()
	        .statusCode(200).body(is("Max anz erreicht"));

		}

	@Test
	@Order(5)
	public void getContractFeatures() {
		Response res = given()
		.queryParam("ctr_id", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("feature");	
		
		res.then().statusCode(200);
		List<Feature> features = Arrays.asList(res.getBody().as(Feature[].class));
		Assertions.assertEquals( FA.getNumber(), features.get(0).getNumber());
		Assertions.assertEquals( FB.getNumber(), features.get(1).getNumber());
		Assertions.assertEquals( 3, features.size());
	}

	@Test
	@Order(6)
	public void removeFeatureFromContract() {
		 FB.setId(11l);
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FB)
	        .when()
	        .delete("feature")	
	        .then()
	        .statusCode(200).body(is("true"));
    }

	@Test
	@Order(7)
	public void getContractFeatures2() {
        contractC.setId(1l);
		Response res = given()
		.queryParam("ctr_id", contractC.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("feature");	
       
		res.then().statusCode(200);
		List<Feature> features = Arrays.asList(res.getBody().as(Feature[].class));
		Assertions.assertEquals( FA.getNumber(), features.get(0).getNumber());
		Assertions.assertEquals( FC.getNumber(), features.get(1).getNumber());
		Assertions.assertEquals( 2, features.size());
	}

	@Test
	@Order(8)
	public void updateFeature(){
        Feature FX = new Feature("123456789");
		FX.setId(10l);
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(FX)
        .when()
		.post("feature")	
		.then()
		.statusCode(200).body(is("true"));	
		
		Response res = given()
		.queryParam("number", FX.getNumber())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("feature");	
       
		res.then().statusCode(200);
		List<Feature> features = Arrays.asList(res.getBody().as(Feature[].class));
		Assertions.assertEquals( FX.getNumber(), features.get(0).getNumber());
	
	}

	@Test
	@Order(10)
	public void removeAllFeatureFromContract() {
        Contract contractC = new Contract("3.3.2020", "3.3.2021", "ver1", "5678");
		contractC.setId(1l);	 
        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(contractC)
        .when()
        .delete("/feature/all")	
        .then()
        .statusCode(200).body(is("true"));

        Response res = given()
		.queryParam("ctrId", contractC.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("/feature");	
        res.then().statusCode(200);
		List<Feature> features = Arrays.asList(res.getBody().as(Feature[].class));
		Assertions.assertEquals( 0, features.size());
	}
}