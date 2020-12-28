package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import javax.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;

import de.hse.swa.jaxquarkus.step4.model.Phone;
import de.hse.swa.jaxquarkus.step4.model.User;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class user_t{
	

	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);
	private static User usrB = new User("Bemail", "Busername", "Bpassword", "Bfirst", "Blast",  false);	
	private static User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true);
	
	private static Phone phoneA = new Phone("Anumber", "Atype");  
	private static Phone phoneB = new Phone("Bnumber", "Btype");
	private static Phone phoneC = new Phone("Cnumber", "Ctype");
	@Test
	@Order(1)
	public void getAnzUsers() {
		Response response =
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .when()
		        .get("/users");
		        response
		        .then().statusCode(200);

       List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
 		Assertions.assertEquals( 2, usrs.size());
	}
	
	@Test
	@Order(2)
	public void GetUsers() {
   	Response response =
			        given()
			        .contentType(MediaType.APPLICATION_JSON)
			        .when()
			        .get("/users");
			        response
			        .then().statusCode(200);
       
	List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
		Assertions.assertEquals( usrA.getLastName(), usrs.get(0).getLastName());
		Assertions.assertEquals( usrB.getLastName(), usrs.get(1).getLastName());
	}
	
	
	@Test
	@Order(3)
	public void GetUser() {
		Long id = 1l;
		Response response = 
	        given()
	        .pathParam("id", id)
	        .contentType(MediaType.APPLICATION_JSON)
	        .when()
	        .get("/users/{id}");
			response.then().statusCode(200);
			
			User usr = response.getBody().as(User.class);
			Assertions.assertEquals( usrA.getLastName(), usr.getLastName());
	}

	
	@Test
	@Order(4)
	public void AddUser() {
		
		Response response = 
				 given()
				 .contentType(MediaType.APPLICATION_JSON)
				 .body(usrC)
				 .when()
				 .put("/users");		
				 response.then().statusCode(204);
		          
//	     response =
//      	        given()     	    
//      	        .contentType(MediaType.APPLICATION_JSON)
//      	        .when()
//      	        .get("/users");		      	    	
//  	        	response
//      	        .then()
//      	        .statusCode(200);
//		      	        
//	      		List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
//      			Assertions.assertEquals( usrA.getLastName(), usrs.get(0).getLastName());
//      			Assertions.assertEquals( usrB.getLastName(), usrs.get(1).getLastName());
//      			Assertions.assertEquals( usrC.getLastName(), usrs.get(2).getLastName());	
//      	  		Assertions.assertEquals( 3, usrs.size());
	} 
//	@Test
//	@Order(5)
//	public void UpdateUser() { 
//		Long id = 10l;
//		usrC.setId(id);
//		usrC.setLastName("Hans");
//		Response response = 
//		        	given()
//		        	.contentType(MediaType.APPLICATION_JSON)
//		        	.body(usrC)
//		        	.when()
//		        	.post("/users");		
//					response.then().statusCode(204); 
//
//          response =
//      	        given()
//      	        .pathParam("id", id)
//      	        .contentType(MediaType.APPLICATION_JSON)
//      	        .when()
//      	        .get("/users/{id}");
//          		response.then().statusCode(200);
//	      	        
//      	    User usr = response.getBody().as(User.class);
//      		Assertions.assertEquals( "Hans", usr.getLastName());
//	}
//		
//	@Test
//	@Order(6)
//	public void DeleteUser() {
//		
//		usrA.setId(1l);
//		Response response = 
//		        given()
//		        .contentType(MediaType.APPLICATION_JSON)
//		        .body(usrA)
//		        .when()
//		        .delete("/users");	
//				response.then().statusCode(204);  
//		          
//	     response =
//  	        given()
//  	        .contentType(MediaType.APPLICATION_JSON)
//  	        .when()
//  	        .get("/users");	
//  	        response
//  	        .then()
//  	        .statusCode(200);
//  	        
//      		List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
//      		Assertions.assertEquals( 2, usrs.size());
//	}
	@Test
	@Order(7)
	public void addPhoneToUser(){
		
		 given()
		 	.pathParam("id", 10l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneA)
	        .when()
	        .post("/phones/add/{id}")	
	        .then()
	        .statusCode(200).body(is("User added"));

		}
	
	@Test
	@Order(8)
	public void addPhoneToUserDuplicate(){
		
		 given()
		    .pathParam("id", 10l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneA)
	        .when()
	        .post("/phones/add/{id}")	
	        .then()
	        .statusCode(200).body(is("Doppelte Nr entdeckt bei User: 10"));

		}
	
	@Test
	@Order(9)
	public void addPhoneToUser2(){

		 given()
		 .pathParam("id", 10l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneB)
	        .when()
	        .post("/phones/add/{id}")	
	        .then()
	        .statusCode(200).body(is("User added"));

		}
	@Test
	@Order(10)
	public void addPhoneToUserToMannyNumbers(){
		
		 given()
		    .pathParam("id", 10l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneC)
	        .when()
	        .post("/phones/add/{id}")	
	        .then()
	        .statusCode(200).body(is("Max anz erreicht"));

		}
	// @Test
	// @Order(11)
	// public void removePhoneFromUser() {
	// 	 phoneB.setId(11l);
		 
	// 	 given()
	//         .contentType(MediaType.APPLICATION_JSON)
	//         .body(phoneB)
	//         .when()
	//         .delete("/phones/remove")	
	//         .then()
	//         .statusCode(200).body(is("true"));
	// }
	@Test
	@Order(12)
	public void getUserPhones() {
		Response res = given()
		.pathParam("id", 10l)
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/phones/{id}");	
       
		res.then().statusCode(200);
//		List<Phone> phones = Arrays.asList(res.getBody().as(Phone[].class));
//		Assertions.assertEquals( phoneA.getNumber(), phones.get(0).getNumber());
////		Assertions.assertEquals( phoneB.getNumber(), phones.get(1).getNumber());
//		Assertions.assertEquals( 1, phones.size());
	}
	
	
	
	
	
}