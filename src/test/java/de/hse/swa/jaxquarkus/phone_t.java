package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.hse.swa.jaxquarkus.step4.model.Phone;
import de.hse.swa.jaxquarkus.step4.model.User;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class phone_t{
	
	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);
	private static User usrB = new User("Bemail", "Busername", "Bpassword", "Bfirst", "Blast",  false);	
	private static User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast",  false);	
	private static Phone phoneA = new Phone("Anumber", "Atype");   // 2 phone nuumbers? number1 typ1 number2 typ2
	private static Phone phoneB = new Phone("Bnumber", "Btype");
	private static Phone phoneC = new Phone("Cnumber", "Ctype");
	//private static Phone phoneC = new Phone("Cnumber", "Ctype");


	
	@Test
	@Order(1)
	public void getPhones() {
    	Response response =
        given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/phones");

        response
        .then()
        .statusCode(200);

	List<Phone> phones = Arrays.asList(response.getBody().as(Phone[].class));
		Assertions.assertEquals( "mobile", phones.get(0).getType());
		Assertions.assertEquals( "landline", phones.get(1).getType());
	}

	@Test
	@Order(2)
	public void getPhoneByNumber() {
		String number = "567892";
		Response response = 
        given()
        .pathParam("number", number)
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/phones/{number}");
		response.then().statusCode(200);
		Phone phone = response.getBody().as(Phone.class);
		Assertions.assertEquals( "567892", phone.getNumber());
	}
	
	@Test
	@Order(3)
	public void Addphone() {
	//	usrA.printUser();
		usrB.setId(1l);
		usrB.toString();
		//phoneA.printPhone();
	//	phoneA.setUser(usrB);
		phoneA.toString();
		System.out.println("Ende test klasse:");
		Response response = 
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(phoneA)
		          .when()
		          .put("/phones");		
		          response.then().statusCode(204);
	} 
	
	@Test
	@Order(4)
	public void getPhonesByUsers() {
		
		usrA.setId(1l);
		Response response = 
        given()
        .pathParam("usr_id", usrA.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/phones/{usr_id}");
		response.then().statusCode(200);
		Phone phone = response.getBody().as(Phone.class);
		Assertions.assertEquals( phoneA.getNumber(), phone.getNumber());
	}

	@Test
	@Order(5)
	public void updatePhone() { 
		phoneA.setId(1l);
		phoneA.setType("home");
		phoneA.setNumber("123456");
		Response response = 
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(phoneA)
		          .when()
		          .post("/phones");		
		          response.then().statusCode(204);      
	}


	@Test
	@Order(6)
	public void removePhone() {

		phoneB.setId(2l);
		Response response = 
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(phoneB)
		          .when()
		          .delete("/phones");		
		          response.then().statusCode(204);  
	}


}