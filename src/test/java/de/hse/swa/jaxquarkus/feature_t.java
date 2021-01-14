package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
	private static Company companyB = new Company("Bname", "Bdepartment", "Bstreet", 12345, "Bstate", "Bcountry");
	private static Company companyC = new Company("Cname", "Cdepartment", "Cstreet", 12345, "Cstate", "Ccountry");	
	
	private static Contract contractA = new Contract("1.1.2020", "1.1.2021", "ver1","1234");
	private static Contract contractB = new Contract("2.2.2020", "2.2.2021", "ver2", "4321");
	private static Contract contractC = new Contract("3.3.2020", "3.3.2021", "ver1", "5678");
	
	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);
	private static User usrB = new User("Bemail", "Busername", "Bpassword", "Bfirst", "Blast",  false);	
	private static User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true);
		
	private static IpNumber IpA = new IpNumber("111.111.111.111");
	private static IpNumber IpB = new IpNumber("222.222.222.222");
	private static IpNumber IpC = new IpNumber("333.333.333.333");
	

	 
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
			.then().statusCode(204);
	
		 given()
		 .pathParam("companyId", 4l)
		 .contentType(MediaType.APPLICATION_JSON)
		 .body(contractA)
		 .when()
		 .put("contract/{companyId}")
		 .then().statusCode(200).body(is("Contract added"));    	
		 
        given()
        .pathParam("id", 4l)
        .contentType(MediaType.APPLICATION_JSON)
        .body(FA)//1
        .when()
        .put("feature/{id}")	
        .then()
        .statusCode(200).body(is("true"));

    }
	// feature nicht mehr unique
//	@Test
//	@Order(2)
//	public void addFeatureToContractDuplicate(){
//		
//        given()
//        .pathParam("id", 1l)
//        .contentType(MediaType.APPLICATION_JSON)
//        .body(FA)//nix
//        .when()
//        .put("feature/{id}")	
//        .then()
//        .statusCode(200).body(is("true"));
//
//    }
//	
	@Test
	@Order(3)
	public void addFeatureToContract2(){

		 given()
		 .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FB)//2
	        .when()
	        .put("feature/{id}")	
	        .then()
            .statusCode(200).body(is("true"));
            
		 given()
		 .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FC)//3
	        .when()
	        .put("feature/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));

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
	        .statusCode(200).body(is("false"));

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
		Assertions.assertEquals( FC.getNumber(), features.get(2).getNumber());
		Assertions.assertEquals( 3, features.size());
	}

	@Test
	@Order(6)
	public void removeFeatureFromContract() {
		 FA.setId(1l);
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
        contractA.setId(1l);
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
        Feature FX = new Feature("123456789");
		FX.setId(2l);
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
	@Order(9)
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
	
	@Test
	@Order(10)
	public void deleteall(){
		//Delete all
			companyOrm.deleteall();			
	}
	
}