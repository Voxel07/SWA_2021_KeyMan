package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import javax.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;

import de.hse.swa.jaxquarkus.step4.model.User;

import org.json.JSONObject;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class user_t{
	

	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);
	private static User usrB = new User("Bemail", "Busername", "Bpassword", "Bfirst", "Blast",  false);	
	private static User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true);

//	@Test
//	@Order(1)
//	public void GetUsers() {
//    	Response response =
//			        given()
//			        .contentType(MediaType.APPLICATION_JSON)
//			        .when()
//			        .get("/users");
//			        response
//			        .then().statusCode(200);
//        
//	List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
//		Assertions.assertEquals( usrA.getLastName(), usrs.get(0).getLastName());
//		Assertions.assertEquals( usrB.getLastName(), usrs.get(1).getLastName());
//	}
//	
//	
//	@Test
//	@Order(2)
//	public void GetUser() {
//		Long id = 1l;
//		Response response = 
//	        given()
//	        .pathParam("id", id)
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .when()
//	        .get("/users/{id}");
//			response.then().statusCode(200);
//			
//			User usr = response.getBody().as(User.class);
//			Assertions.assertEquals( usrA.getLastName(), usr.getLastName());
//	}
//
//	
//	@Test
//	@Order(3)
//	public void AddUser() {
//		
//		Response response = 
//				 given()
//				 .contentType(MediaType.APPLICATION_JSON)
//				 .body(usrC)
//				 .when()
//				 .put("/users");		
//				 response.then().statusCode(204);
//		          
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
//		 
//	} 
//	
//	
//	@Test
//	@Order(4)
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
//	@Order(5)
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
	@Order(6)
	public void addPhoneToUser(){
		
		JSONObject jo = new JSONObject();
				jo.put("id",1);
				jo.put("type","landline");
				jo.put("number", "8150247");
				System.out.println("Da swurde gesendet:"+jo.toString());
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(jo.toString())
	        .when()
	        .post("/users/update")	
	        .then()
	        .statusCode(204);

		}
	@Test
	@Order(7)
	public void addPhoneToUser2(){
		
		JSONObject jo = new JSONObject();
				jo.put("id",1);
				jo.put("type","mobile");
				jo.put("number", "12343534");
	
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(jo.toString())
	        .when()
	        .post("/users/update")	
	        .then()
	        .statusCode(204);

		}
//	@Test
//	@Order(8)
//	public void addPhoneToUser3(){
//		
//		JSONObject jo = new JSONObject();
//				jo.put("id",1);
//				jo.put("type","landline");
//				jo.put("number", "56756345");
//	
//		 given()
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .body(jo.toString())
//	        .when()
//	        .post("/users/update")	
//	        .then()
//	        .statusCode(204);
//
//		}
	
	
	
	
}