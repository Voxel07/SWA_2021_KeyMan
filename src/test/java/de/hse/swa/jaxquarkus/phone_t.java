package de.hse.swa.jaxquarkus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.hse.swa.jaxquarkus.step4.model.Company;
import de.hse.swa.jaxquarkus.step4.model.Contract;
import de.hse.swa.jaxquarkus.step4.model.IpNumber;
import de.hse.swa.jaxquarkus.step4.model.Phone;
import de.hse.swa.jaxquarkus.step4.model.User;
import de.hse.swa.jaxquarkus.step4.orm.CompanyOrm;
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
    private static Company companyA = new Company("Aname", "Adepartment", "Astreet", 12345, "Astate", "Acountry");	
	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);

	@Inject
    CompanyOrm companyOrm;
	
	@Test
	@Order(1)
	public void addPhoneToUser(){
		
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(companyA)
			.when()
			.put("/company")
			.then().statusCode(204);
		
		Response response =
				given()
				.contentType(MediaType.APPLICATION_JSON)
				.when()
				.get("/company");
				
				response
				.then()
				.statusCode(200);
				
	 List<Company> companys = Arrays.asList(response.getBody().as(Company[].class));
		companyA.setId(companys.get(0).getId());
		
		given()
		 .pathParam("companyId", companyA.getId())
		 .contentType(MediaType.APPLICATION_JSON)
		 .body(usrA)
		 .when()
		 .put("/user/{companyId}")	
		 .then().statusCode(200).body(is("true"));
		
		response =
     	        given()     	    
     	        .contentType(MediaType.APPLICATION_JSON)
     	        .when()
     	        .get("/user");		      	    	
 	        	response
     	        .then()
     	        .statusCode(200);
		      	        
	      		List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
	      		usrA.setId(usrs.get(0).getId());
		
		 given()
		 	.pathParam("id", companyA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneA)
	        .when()
	        .put("/phone/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));
		 
		 response = 
				 	given()
					.queryParam("usr_id", usrA.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.when()
					.get("/phone");	
					
		      		response.then().statusCode(200);
					List<Phone> phones = Arrays.asList(response.getBody().as(Phone[].class));
					phoneA.setId(phones.get(0).getId());
		}
	
	@Test
	@Order(2)
	public void addPhoneToUserDuplicate(){
		
		 given()
		    .pathParam("id", companyA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneA)
	        .when()
	        .put("/phone/{id}")	
	        .then()
	        .statusCode(200).body(is("false"));

		Response response = 
				 	given()
					.queryParam("usr_id", usrA.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.when()
					.get("/phone");	
					
		      		response.then().statusCode(200);
					List<Phone> phones = Arrays.asList(response.getBody().as(Phone[].class));
					phoneA.setId(phones.get(0).getId());
		}
	
	@Test
	@Order(3)
	public void addPhoneToUser2(){

		 given()
		 .pathParam("id", companyA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneB)
	        .when()
	        .put("/phone/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));
		 
		 Response response = 
				 	given()
					.queryParam("usr_id", usrA.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.when()
					.get("/phone");	
					
		      		response.then().statusCode(200);
					List<Phone> phones = Arrays.asList(response.getBody().as(Phone[].class));
					phoneB.setId(phones.get(0).getId());

		}
	@Test
	@Order(4)
	public void addPhoneToUserToMannyNumbers(){
		
		 given()
		    .pathParam("id", companyA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneC)
	        .when()
	        .put("/phone/{id}")	
	        .then()
	        .statusCode(200).body(is("false"));

		}

	@Test
	@Order(5)
	public void getUserPhones() {
		Response res = given()
		.queryParam("usr_id", usrA.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("/phone");	
		
		res.then().statusCode(200);
		List<Phone> phones = Arrays.asList(res.getBody().as(Phone[].class));
		Assertions.assertEquals( phoneA.getNumber(), phones.get(0).getNumber());
		Assertions.assertEquals( phoneB.getNumber(), phones.get(1).getNumber());
		Assertions.assertEquals( 2, phones.size());
	}

	@Test
	@Order(6)
	public void removePhoneFromUser() {
		 //phoneB.setId(1l);
		 
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneA)
	        .when()
	        .delete("/phone")	
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
		.get("/phone");	
       
		res.then().statusCode(200);
		List<Phone> phones = Arrays.asList(res.getBody().as(Phone[].class));
		Assertions.assertEquals( phoneB.getNumber(), phones.get(0).getNumber());
		Assertions.assertEquals( 1, phones.size());
	}

	@Test
	@Order(8)
	public void updatePhone(){
		phoneB.setType("ich bin geaendert");
		phoneB.setNumber("9876543");
		//phoneB.setId(2l);
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(phoneB)
        .when()
		.post("/phone")	
		.then()
		.statusCode(200).body(is("true"));	
		
		Response res = given()
		.queryParam("number", phoneB.getNumber())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("/phone");	
       
		res.then().statusCode(200);
		List<Phone> phones = Arrays.asList(res.getBody().as(Phone[].class));
		Assertions.assertEquals( phoneB.getNumber(), phones.get(0).getNumber());
		Assertions.assertEquals( phoneB.getType(), phones.get(0).getType());
		Assertions.assertEquals( 1, phones.size());
	
	}
	@Test
	@Order(9)
	public void updatePhonenoachmal(){
		phoneB.setType("ich bin geaendert nochmal nochmal");
		phoneB.setNumber("Bnumber");
		//phoneB.setId(companyA.getId());
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(phoneB)
        .when()
		.post("/phone")
		.then()
		.statusCode(200).body(is("true"));	
	}

	@Test
	@Order(10)
	public void removeAllPhoneFromUser() {
	User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true); 
		//phoneB.setId(1l);
		usrC.setId(companyA.getId());
	//	phoneOrm.removeAllPhonesFromUser(usrC);
		 
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(usrC)
	        .when()
	        .delete("/phone/all")	
	        .then()
	        .statusCode(200).body(is("true"));
	}
	@Test
	@Order(11)
	public void deleteall(){
		//Delete all
			companyOrm.deleteall();			
	}
}