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

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class company_t{
	
	private static Company companyA = new Company("Aname", "Adepartment", "Astreet", 12345, "Astate", "Acountry");
	private static Company companyB = new Company("Bname", "Bdepartment", "Bstreet", 12345, "Bstate", "Bcountry");
	private static Company companyC = new Company("Cname", "Cdepartment", "Cstreet", 12345, "Cstate", "Ccountry");	
	private static Contract contractB = new Contract("1.1.2021", "1.1.2022", "ver2");
	private static User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true);
	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);
	
	@Inject
    CompanyOrm companyOrm;
	
	@Test
	@Order(1)
	public void AddCompany() {
		Response response = 
				given()
				.contentType(MediaType.APPLICATION_JSON)
				.body(companyA)
					.when()
					.put("/company");		
					response.then().statusCode(200)
					.body(is("Company hinzugefügt"));
												
		 response = 
				given()
				.contentType(MediaType.APPLICATION_JSON)
				.body(companyB)
					.when()
					.put("/company");		
		 			response.then().statusCode(200)
		 			.body(is("Company hinzugefügt"));				
						
		 response = 
				given()
				.contentType(MediaType.APPLICATION_JSON)
				.body(companyC)
					.when()
					.put("/company");		
					response.then().statusCode(200)
					 .body(is("Company hinzugefügt"));
	}
					
	@Test
	@Order(2)
	public void GetCompanys() {
		Response response =
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("/company");
		
		response
		.then()
		.statusCode(200);
		
	List<Company> companys = Arrays.asList(response.getBody().as(Company[].class));
		Assertions.assertEquals( companyA.getName(), companys.get(0).getName());
		Assertions.assertEquals( companyB.getName(), companys.get(1).getName());
		Assertions.assertEquals( companyC.getName(), companys.get(2).getName());
		
		companyA.setId(companys.get(0).getId());
		companyB.setId(companys.get(1).getId());
		companyC.setId(companys.get(2).getId());
						
	}


	@Test
	@Order(3)
	public void GetCompany() {
		Response response = 
		given()
		.pathParam("id", companyA.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("/company/{id}");
		response.then().statusCode(200);
		Company company = response.getBody().as(Company.class);
		Assertions.assertEquals( companyA.getName(), company.getName());
	}


	@Test
	@Order(4)
	public void UpdateCompany() { 
		companyC.setStreet("Helferstr");
		Response response = 
				given()
				.contentType(MediaType.APPLICATION_JSON)
				.body(companyC)
					.when()
					.post("/company");		
					response.then().statusCode(204);      
		response = 
		given()
		.pathParam("id", companyC.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("/company/{id}");
		response.then().statusCode(200);
		Company company = response.getBody().as(Company.class);
		Assertions.assertEquals( companyC.getStreet(), company.getStreet());
	}
		

	@Test
	@Order(5)
	public void DeleteCompany() {
		
		//user für company
		given()
		.pathParam("companyId", companyB.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.body(usrC)
		.when()
		.put("/user/{companyId}")		
		.then().statusCode(200);
		
		Response response =
     	        given()     	    
     	        .contentType(MediaType.APPLICATION_JSON)
     	        .when()
     	        .get("/user");		      	    	
 	        	response
     	        .then()
     	        .statusCode(200);
		      	        
				List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
				Assertions.assertEquals( usrC.getUsername(), usrs.get(0).getUsername());
				Assertions.assertEquals( 1, usrs.size());
				  
	      		usrC.setId(usrs.get(0).getId());
		
		//user f�r company
		given()
		.pathParam("companyId", companyB.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.body(usrA)
		.when()
		.put("/user/{companyId}")		
		.then().statusCode(200);
		
		response =
     	        given()     	    
     	        .contentType(MediaType.APPLICATION_JSON)
     	        .when()
     	        .get("/user");		      	    	
 	        	response
     	        .then()
     	        .statusCode(200);
		      	        
				List<User> usrs1 = Arrays.asList(response.getBody().as(User[].class));
				Assertions.assertEquals( 2, usrs1.size());
				Assertions.assertEquals( usrC.getUsername(), usrs1.get(0).getUsername());
				Assertions.assertEquals( usrA.getUsername(), usrs1.get(1).getUsername());
	      		usrA.setId(usrs1.get(1).getId());
				
		given()
		.pathParam("companyId", companyB.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.body(contractB)
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
					Assertions.assertEquals( contractB.getVersion(), ctr.get(0).getVersion());
					contractB.setId(ctr.get(0).getId());	

		given()
		.queryParam("usrId", usrC.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.body(contractB)
		.when()
		.post("contract")
		.then().statusCode(200).body(is("true"));
			
		 response = 
			given()
			.contentType(MediaType.APPLICATION_JSON)
			.body(companyA)
			.when()
			.delete("/company");		
			response.then().statusCode(204);  
			}	
	@Test
	@Order(6)
	public void deleteall(){
		//Delete all
			companyOrm.deleteall();			
	}
}