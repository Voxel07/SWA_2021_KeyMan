package de.hse.swa.jaxquarkus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.hse.swa.jaxquarkus.step4.model.Phone;
import de.hse.swa.jaxquarkus.step4.model.User;
import de.hse.swa.jaxquarkus.step4.orm.PhoneOrm;

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
public class phone_t{
	private static Phone phoneA = new Phone("Anumber", "Atype");  
	private static Phone phoneB = new Phone("Bnumber", "Btype");
	private static Phone phoneC = new Phone("Cnumber", "Ctype");
	
	@Inject
	PhoneOrm phoneOrm;
	
	@Test
	@Order(1)
	public void addPhoneToUser(){
		
		 given()
		 	.pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneA)
	        .when()
	        .put("/phones/add/{id}")	
	        .then()
	        .statusCode(200).body(is("Phone added"));

		}
	
	@Test
	@Order(2)
	public void addPhoneToUserDuplicate(){
		
		 given()
		    .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneA)
	        .when()
	        .put("/phones/add/{id}")	
	        .then()
	        .statusCode(200).body(is("Doppelte Nr entdeckt bei User: 10"));

		}
	
	@Test
	@Order(3)
	public void addPhoneToUser2(){

		 given()
		 .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneB)
	        .when()
	        .put("/phones/add/{id}")	
	        .then()
	        .statusCode(200).body(is("Phone added"));

		}
	@Test
	@Order(4)
	public void addPhoneToUserToMannyNumbers(){
		
		 given()
		    .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneC)
	        .when()
	        .put("/phones/add/{id}")	
	        .then()
	        .statusCode(200).body(is("Max anz erreicht"));

		}

	@Test
	@Order(5)
	public void getUserPhones() {
		Response res = given()
		.queryParam("usr_id", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("/phones");	
		
		res.then().statusCode(200);
		List<Phone> phones = Arrays.asList(res.getBody().as(Phone[].class));
		Assertions.assertEquals( phoneA.getNumber(), phones.get(0).getNumber());
		Assertions.assertEquals( phoneB.getNumber(), phones.get(1).getNumber());
		Assertions.assertEquals( 2, phones.size());
	}

	@Test
	@Order(6)
	public void removePhoneFromUser() {
		 phoneB.setId(1l);
		 
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneA)
	        .when()
	        .delete("/phones")	
	        .then()
	        .statusCode(200).body(is("true"));
	}

	@Test
	@Order(7)
	public void getUserPhones2() {
		Response res = given()
		.queryParam("number", phoneB.getNumber())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("/phones");	
       
		res.then().statusCode(200);
		List<Phone> phones = Arrays.asList(res.getBody().as(Phone[].class));
		Assertions.assertEquals( phoneB.getNumber(), phones.get(0).getNumber());
		Assertions.assertEquals( 1, phones.size());
	}

	@Test
	@Order(8)
	public void updatePhone(){
		phoneB.setType("ich bin geändert");
		phoneB.setNumber("9876543");
		phoneB.setId(2l);
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(phoneB)
        .when()
		.post("/phones")	
		.then()
		.statusCode(200).body(is("true"));	
		
		Response res = given()
		.queryParam("number", phoneB.getNumber())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("/phones");	
       
		res.then().statusCode(200);
		List<Phone> phones = Arrays.asList(res.getBody().as(Phone[].class));
		Assertions.assertEquals( phoneB.getNumber(), phones.get(0).getNumber());
		Assertions.assertEquals( phoneB.getType(), phones.get(0).getType());
		Assertions.assertEquals( 1, phones.size());
	
	}
	@Test
	@Order(9)
	public void updatePhonenoachmal(){
		phoneB.setType("ich bin geändert nochmal nochmal");
		phoneB.setNumber("Bnumber");
		phoneB.setId(2l);
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(phoneB)
        .when()
		.post("/phones")
		.then()
		.statusCode(200).body(is("false"));	
	}

	@Test
	@Order(10)
	public void removeAllPhoneFromUser() {
	User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true); 
		phoneB.setId(1l);
		usrC.setId(1l);
	//	phoneOrm.removeAllPhonesFromUser(usrC);
		 
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(usrC)
	        .when()
	        .delete("/phones/all")	
	        .then()
	        .statusCode(200).body(is("true"));
	}
}