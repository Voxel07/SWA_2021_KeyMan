package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.IpNumberOrm;

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
public class ipNumber_t{
    private static IpNumber IpA = new IpNumber("111.111.111.111");
	private static IpNumber IpB = new IpNumber("222.222.222.22");
	private static IpNumber IpC = new IpNumber("333.333.333.33");
	
	@Inject
	IpNumberOrm ipNumberOrm;
	
	@Test
	@Order(1)
	public void addPhoneToUser(){
		
		 given()
		 	.pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpA)
	        .when()
	        .put("/IpNumber/add/{id}")	
	        .then()
	        .statusCode(200).body(is("Phone added"));

		}
	
	@Test
	@Order(2)
	public void addPhoneToUserDuplicate(){
		
		 given()
		    .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpA)
	        .when()
	        .put("/IpNumber/add/{id}")	
	        .then()
	        .statusCode(200).body(is("Doppelte Nr entdeckt bei User: 10"));

		}
	
	@Test
	@Order(3)
	public void addPhoneToUser2(){

		 given()
		 .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpB)
	        .when()
	        .put("/IpNumber/add/{id}")	
	        .then()
	        .statusCode(200).body(is("Phone added"));

		}
	@Test
	@Order(4)
	public void addPhoneToUserToMannyNumbers(){
		
		 given()
		    .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpC)
	        .when()
	        .put("/IpNumber/add/{id}")	
	        .then()
	        .statusCode(200).body(is("Max anz erreicht"));

		}

	@Test
	@Order(5)
	public void getUserIpNumber() {
		Response res = given()
		.queryParam("usr_id", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("/IpNumber");	
		
		res.then().statusCode(200);
		List<IpNumber> IpNumber = Arrays.asList(res.getBody().as(IpNumber[].class));
		Assertions.assertEquals( IpA.getIpNumber(), IpNumber.get(0).getIpNumber());
		Assertions.assertEquals( IpB.getIpNumber(), IpNumber.get(1).getIpNumber());
		Assertions.assertEquals( 2, IpNumber.size());
	}

	@Test
	@Order(6)
	public void removePhoneFromUser() {
		 IpB.setId(1l);
		 
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpA)
	        .when()
	        .delete("/IpNumber")	
	        .then()
	        .statusCode(200).body(is("true"));
	}

	@Test
	@Order(7)
	public void getUserIpNumber2() {
		Response res = given()
		.queryParam("number", IpB.getIpNumber())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("/IpNumber");	
       
		res.then().statusCode(200);
		List<IpNumber> IpNumbers = Arrays.asList(res.getBody().as(IpNumber[].class));
		Assertions.assertEquals( IpB.getIpNumber(), IpNumbers.get(0).getIpNumber());
		Assertions.assertEquals( 1, IpNumbers.size());
	}

	@Test
	@Order(8)
	public void updatePhone(){
		IpB.setIpNumber("9876543");
		IpB.setId(2l);
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(IpB)
        .when()
		.post("/IpNumber")	
		.then()
		.statusCode(200).body(is("true"));	
		
		Response res = given()
		.queryParam("number", IpB.getIpNumber())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("/IpNumber");	
       
		res.then().statusCode(200);
		List<IpNumber> IpNumbers = Arrays.asList(res.getBody().as(IpNumber[].class));
		Assertions.assertEquals( IpB.getIpNumber(), IpNumbers.get(0).getIpNumber());
		Assertions.assertEquals( 1, IpNumbers.size());
	
	}
	@Test
	@Order(9)
	public void updatePhonenoachmal(){
		IpB.setIpNumber("Bnumber");
		IpB.setId(2l);
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(IpB)
        .when()
		.post("/IpNumber")
		.then()
		.statusCode(200).body(is("false"));	
	}

	@Test
	@Order(10)
	public void removeAllPhoneFromUser() {
	User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true); 
		IpB.setId(1l);
		usrC.setId(1l);
	//	phoneOrm.removeAllIpNumberFromUser(usrC);
		 
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(usrC)
	        .when()
	        .delete("/IpNumber/all")	
	        .then()
	        .statusCode(200).body(is("true"));
	}
}
