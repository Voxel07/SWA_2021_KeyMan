package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.*;

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
    
    private static Company companyA = new Company("Aname", "Adepartment", "Astreet", 12345, "Astate", "Acountry");

	private static Contract contractA = new Contract("1.1.2020", "1.1.2021", "ver1");

	 
	@Inject
    CompanyOrm companyOrm;

    
	@Test
	@Order(1)
	public void addFeatureToContract(){
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(companyA)
			.when()
			.put("/company")
			.then().statusCode(200)
			.body(is("Company hinzugefügt"));
		
		Response response =
				given()
				.contentType(MediaType.APPLICATION_JSON)
				.when()
				.get("/company");
				
				response
				.then()
				.statusCode(200);
				
	 List<Company> companys = Arrays.asList(response.getBody().as(Company[].class));
	 Assertions.assertEquals( 1, companys.size());
	 Assertions.assertEquals( companyA.getName(), companys.get(0).getName());

		companyA.setId(companys.get(0).getId());
		
		 given()
		 .pathParam("companyId", companyA.getId())
		 .contentType(MediaType.APPLICATION_JSON)
		 .body(contractA)
		 .when()
		 .put("contract/{companyId}")
		 .then().statusCode(200);    	
		 
		 Response res =
			        given()
			        .contentType(MediaType.APPLICATION_JSON)
			        .when()
			        .get("contract");
			    	
					res.then().statusCode(200);
					List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
					Assertions.assertEquals(1, ctr.size());
	 				Assertions.assertEquals( contractA.getVersion(), ctr.get(0).getVersion());
					contractA.setId(ctr.get(0).getId());	
					
        given()
        .pathParam("id", contractA.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .body(FA)//1
        .when()
        .put("feature/{id}")	
        .then()
        .statusCode(200).body(is("true"));
        
         res = given()
        		.queryParam("ctr_id", contractA.getId())
        		.contentType(MediaType.APPLICATION_JSON)
        		.when()
        		.get("feature");	
        		
        		res.then().statusCode(200);
				List<Feature> features = Arrays.asList(res.getBody().as(Feature[].class));
				Assertions.assertEquals(1, features.size());
				Assertions.assertEquals(FA.getNumber(), features.get(0).getNumber());
        		FA.setId(features.get(0).getId());
    }

	@Test
	@Order(3)
	public void addFeatureToContract2(){
		
		 given()
		 .pathParam("id", contractA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FB)//2
	        .when()
	        .put("feature/{id}")	
	        .then()
            .statusCode(200).body(is("true"));
		 		
		 given()
		 .pathParam("id", contractA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FC)//3
	        .when()
	        .put("feature/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));

			Response res = given()
			.queryParam("ctrId", contractA.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.when()
			.get("feature");	
			
			res.then().statusCode(200);
			List<Feature> features = Arrays.asList(res.getBody().as(Feature[].class));
			Assertions.assertEquals(3, features.size());
			Assertions.assertEquals(FA.getNumber(), features.get(0).getNumber());
			Assertions.assertEquals(FB.getNumber(), features.get(1).getNumber());
			Assertions.assertEquals(FC.getNumber(), features.get(2).getNumber());
			FB.setId(features.get(1).getId());
			FC.setId(features.get(2).getId());
		}

	@Test
	@Order(4)
	public void addFeatureToContractToMannyNumbers(){
		
		 given()
		    .pathParam("id", contractA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FD)
	        .when()
	        .put("feature/{id}")	
	        .then()
	        .statusCode(200).body(is("false"));
		}

	@Test
	@Order(5)
	public void getContractFeatures() {
		Response res = given()
		.queryParam("ctrId", contractA.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("feature");	
		
		res.then().statusCode(200);
		List<Feature> features = Arrays.asList(res.getBody().as(Feature[].class));
		Assertions.assertEquals( FA.getNumber(), features.get(0).getNumber());
		Assertions.assertEquals( FB.getNumber(), features.get(1).getNumber());
		Assertions.assertEquals( FC.getNumber(), features.get(2).getNumber());
		Assertions.assertEquals( 3, features.size());
	}

	@Test
	@Order(6)
	public void removeFeatureFromContract() {

		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FA)
	        .when()
	        .delete("feature")	
	        .then()
	        .statusCode(200).body(is("true"));
    }

	@Test
	@Order(7)
	public void getContractFeatures2() {
        //contractA.setId(1l);
		Response res = given()
		.queryParam("ctr_id", contractA.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("feature");	
       
		res.then().statusCode(200);
		List<Feature> features = Arrays.asList(res.getBody().as(Feature[].class));
		Assertions.assertEquals( FB.getNumber(), features.get(0).getNumber());
		Assertions.assertEquals( FC.getNumber(), features.get(1).getNumber());
		Assertions.assertEquals( 2, features.size());
	}

	@Test
	@Order(8)
	public void updateFeature(){
        FB.setNumber("12");
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(FB)
        .when()
		.post("feature")	
		.then()
		.statusCode(200).body(is("true"));	
		
		Response res = given()
		.queryParam("number", FB.getNumber())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("feature");	
       
		res.then().statusCode(200);
		List<Feature> features = Arrays.asList(res.getBody().as(Feature[].class));
		Assertions.assertEquals( FB.getNumber(), features.get(0).getNumber());
	
	}

	@Test
	@Order(9)
	public void removeAllFeatureFromContract() {
        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(contractA)
        .when()
        .delete("/feature/all")	
        .then()
        .statusCode(200).body(is("true"));

        Response res = given()
		.queryParam("ctrId", contractA.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("/feature");	
        res.then().statusCode(200);
		List<Feature> features = Arrays.asList(res.getBody().as(Feature[].class));
		Assertions.assertEquals( 0, features.size());
	}
	
	@Test
	@Order(10)
	public void deleteall(){
		//Delete all
			companyOrm.deleteall();			
	}
	
}