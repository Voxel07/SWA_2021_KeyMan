package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.hse.swa.jaxquarkus.step4.model.Company;
import de.hse.swa.jaxquarkus.step4.model.Contract;
import de.hse.swa.jaxquarkus.step4.model.User;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class company_t{
	
	private static Company companyA = new Company("Aname", "Adepartment", "Astreet", 12345, "Astate", "Acountry");
	private static Company companyB = new Company("Bname", "Bdepartment", "Bstreet", 12345, "Bstate", "Bcountry");
	private static Company companyC = new Company("Cname", "Cdepartment", "Cstreet", 12345, "Cstate", "Ccountry");	
	
	private static Contract contractA = new Contract("1.1.2020", "1.1.2021", "ver1","1234");
	private static Contract contractB = new Contract("1.1.2021", "1.1.2022", "ver2","2234");
	
	private static User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true);
	@Test
	@Order(1)
	public void AddCompany() {
		Response response = 
				given()
				.contentType(MediaType.APPLICATION_JSON)
				.body(companyC)
					.when()
					.put("/companys");		
					response.then().statusCode(204);
			
	} 


	@Test
	@Order(2)
	public void GetCompanys() {
		Response response =
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("/companys");
		
		response
		.then()
		.statusCode(200);
		
	List<Company> companys = Arrays.asList(response.getBody().as(Company[].class));
		Assertions.assertEquals( companyA.getName(), companys.get(0).getName());
		Assertions.assertEquals( companyB.getName(), companys.get(1).getName());
	}


	@Test
	@Order(3)
	public void GetCompany() {
		Long id = 1l;
		Response response = 
		given()
		.pathParam("id", id)
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("/companys/{id}");
		response.then().statusCode(200);
		Company company = response.getBody().as(Company.class);
		Assertions.assertEquals( companyA.getName(), company.getName());
	}


	@Test
	@Order(4)
	public void UpdateCompany() { 
		
		companyC.setId(10l);
		companyC.setStreet("Helferstr");
		Response response = 
				given()
				.contentType(MediaType.APPLICATION_JSON)
				.body(companyC)
					.when()
					.post("/companys");		
					response.then().statusCode(204);      
	}
		

	@Test
	@Order(5)
	public void DeleteCompany() {
		
		//user f�r company
		given()
		.pathParam("companyId", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.body(usrC)
		.when()
		.put("/users/{companyId}")		
		.then().statusCode(200).body(is("true"));
		
		//contract f�r company
		given()
		.pathParam("companyId", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.body(contractB)
		.when()
		.put("contract/{companyId}")
		.then().statusCode(200).body(is("Contract added"));
		
		
		contractB.setId(10l);
		given()
		.queryParam("usrId", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.body(contractB)
		.when()
		.post("contract")
		.then().statusCode(200).body(is("true"));
			
			
//				 
		companyA.setId(1l);
		Response response = 
			given()
			.contentType(MediaType.APPLICATION_JSON)
			.body(companyA)
			.when()
			.delete("/companys");		
			response.then().statusCode(204);  
			}	

}