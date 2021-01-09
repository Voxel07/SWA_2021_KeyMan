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

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.CompanyOrm;

import java.util.Arrays;
import java.util.List;

import de.hse.swa.jaxquarkus.step4.model.*;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class user_t{
	private static Phone phoneA = new Phone("Anumber", "Atype");  
	private static Phone phoneB = new Phone("Bnumber", "Btype");
	private static Phone phoneC = new Phone("Cnumber", "Ctype");
	
    private static IpNumber IpA = new IpNumber("111.111.111.111");
	private static IpNumber IpB = new IpNumber("222.222.222.222");
	private static IpNumber IpC = new IpNumber("333.333.333.333");
	private static IpNumber IpD = new IpNumber("444.444.444.444");


    private static Company companyA = new Company("Aname", "Adepartment", "Astreet", 12345, "Astate", "Acountry");
	private static Company companyB = new Company("Bname", "Bdepartment", "Bstreet", 12345, "Bstate", "Bcountry");
	private static Company companyC = new Company("Cname", "Cdepartment", "Cstreet", 12345, "Cstate", "Ccountry");	
	
	private static Contract contractA = new Contract("1.1.2020", "1.1.2021", "ver1","1234");
	private static Contract contractB = new Contract("2.2.2020", "2.2.2021", "ver2", "4321");
	private static Contract contractC = new Contract("3.3.2020", "3.3.2021", "ver1", "5678");
	
	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);
	private static User usrB = new User("Bemail", "Busername", "Bpassword", "Bfirst", "Blast",  false);	
	private static User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true);
	@Inject
    CompanyOrm companyOrm;

	@Test
	@Order(1)
	public void AddUser() {
		
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(companyA)
			.when()
			.put("/company")
			.then().statusCode(204);
		
		Response response = 
				 given()
				 .pathParam("companyId", 1l)
				 .contentType(MediaType.APPLICATION_JSON)
				 .body(usrA)
				 .when()
				 .put("/user/{companyId}");		
				 response.then().statusCode(200).body(is("true"));
		         
	} 
	
	@Test
	@Order(2)
	public void getAnzUsers() {
		Response response =
     	        given()     	    
     	        .contentType(MediaType.APPLICATION_JSON)
     	        .when()
     	        .get("/user");		      	    	
 	        	response
     	        .then()
     	        .statusCode(200);
		      	        
	      		List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
     			Assertions.assertEquals( usrA.getLastName(), usrs.get(0).getLastName());	
     	  		Assertions.assertEquals( 1, usrs.size());
	}
	
	@Test
	@Order(3)
	public void GetUsers() {
   	Response response =
			        given()
			        .contentType(MediaType.APPLICATION_JSON)
			        .when()
			        .get("/user");
			        response
			        .then().statusCode(200);
       
	List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
		Assertions.assertEquals( usrA.getLastName(), usrs.get(0).getLastName());
	}
		
	@Test
	@Order(4)
	public void GetUser() {
		Long id = 1l;
		Response response = 
	        given()
	        .pathParam("id", id)
	        .contentType(MediaType.APPLICATION_JSON)
	        .when()
	        .get("/user/{id}");
			response.then().statusCode(200);
			
			User usr = response.getBody().as(User.class);
			Assertions.assertEquals( usrA.getLastName(), usr.getLastName());
	}

	@Test
	@Order(5)
	public void UpdateUser() { 
		Long id = 1l;
		usrA.setId(id);
		usrA.setLastName("Hans");
		Response response = 
		        	given()
		        	.contentType(MediaType.APPLICATION_JSON)
		        	.body(usrA)
		        	.when()
		        	.post("/user");		
					response.then().statusCode(204); 

         response =
     	        given()
     	        .pathParam("id", id)
     	        .contentType(MediaType.APPLICATION_JSON)
     	        .when()
     	        .get("/user/{id}");
         		response.then().statusCode(200);
	      	        
     	    User usr = response.getBody().as(User.class);
     		Assertions.assertEquals( "Hans", usr.getLastName());
	}
		
	@Test
	@Order(6)
	public void DeleteUser() {
		
		Phone phoneA = new Phone("Anumber", "Atype"); 
		Phone phoneB = new Phone("Bnumber", "Btype"); 
		usrA.setId(1l);

		given()
		.pathParam("id", usrA.getId())
	   .contentType(MediaType.APPLICATION_JSON)
	   .body(phoneA)
	   .when()
	   .put("/phone/{id}")	
	   .then()
	   .statusCode(200).body(is("true"));
	   given()
		.pathParam("id", usrA.getId())
	   .contentType(MediaType.APPLICATION_JSON)
	   .body(phoneB)
	   .when()
	   .put("/phone/{id}")	
	   .then()
	   .statusCode(200).body(is("true"));


		Response response = 
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(usrA)
		        .when()
		        .delete("/user");	
				response.then().statusCode(204);  
		          
	     response =
 	        given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .when()
 	        .get("/user");	
 	        response
 	        .then()
 	        .statusCode(200);
 	        
     		List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
     		Assertions.assertEquals( 0, usrs.size());
	}
	
	@Test
	@Order(7)
	public void deleteall(){
		//Delete all
			companyOrm.deleteall();			
	}
	//User add connection to Company
}