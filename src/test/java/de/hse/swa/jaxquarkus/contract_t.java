package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.CompanyOrm;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.SequenceGenerator;
import javax.ws.rs.core.MediaType;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class contract_t{
	private static Company companyA = new Company("Aname", "Adepartment", "Astreet", 12345, "Astate", "Acountry");
	private static Contract contractA = new Contract("1.1.2020", "1.1.2021", "ver1");

	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);

	private static IpNumber IpA = new IpNumber("111.111.111.111");
	private static IpNumber IpB = new IpNumber("222.222.222.222");
	private static IpNumber IpC = new IpNumber("333.333.333.333");
	
	private static Feature FA = new Feature("1");
	private static Feature FB = new Feature("2");
	private static Feature FC = new Feature("3");
	
	@Inject
    CompanyOrm companyOrm;
	
	@SequenceGenerator(name = "conSeq", sequenceName = "ZSEQ_con_ID", allocationSize = 1, initialValue = 1)
	
	@Test
	@Order(1)
	public void AddContract() {
		
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
			 .body(contractA)
			 .when()
			 .put("contract/{companyId}")
			 .then().statusCode(200).body(is("Contract added")); 
			 
			 Response res =
				        given()
				        .contentType(MediaType.APPLICATION_JSON)
				        .when()
				        .get("contract");
				    	
						res.then().statusCode(200);
						List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
						contractA.setId(ctr.get(0).getId());	
	} 
	
	@Test
	@Order(2)
	public void addConnectionUserContract() {
		
		 given()
		 .pathParam("companyId", companyA.getId())
		 .contentType(MediaType.APPLICATION_JSON)
		 .body(usrA)
		 .when()
		 .put("/user/{companyId}")		
		 .then().statusCode(200).body(is("true"));
		
		 Response response =
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
		 .queryParam("usrId", usrA.getId())
		 .contentType(MediaType.APPLICATION_JSON)
		 .body(contractA)
		 .when()
		 .post("contract")
		 .then()
		 .statusCode(200).body(is("true"));
		 
		 
	} 
		
	@Test
	@Order(3)
	public void GetContracts() {
    	Response res =
        given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("contract");
    	
		res.then().statusCode(200);
		List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
		Assertions.assertEquals( contractA.getLicenskey(), ctr.get(0).getLicenskey());
		Assertions.assertEquals( 1, ctr.size());
	}
	
	
	@Test
	@Order(4)
	public void getContractByLicenskey() {
		Response res = 
        given()
        .queryParam("licenskey", "1234")
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("contract");
		
		res.then().statusCode(200);
		
		List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
		Assertions.assertEquals( contractA.getLicenskey(), ctr.get(0).getLicenskey());
	}
	
	//Geht nicht da ich noch keine CompanyId gesetzt hab!!!!!
	@Test
	@Order(5)
	public void getContractByCompany() {

		Response res = 
        given()
        .queryParam("company_id", companyA.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("contract");
		res.then().statusCode(200); // list
		
		List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
		Assertions.assertEquals( contractA.getLicenskey(), ctr.get(0).getLicenskey());
	}

	
	@Test
	@Order(6)
	public void UpdateContract() { 
		
		//contractA.setId(2l);
		contractA.setLicenskey("Cool");
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(contractA)
		          .when()
		          .post("contract")		
		          .then().statusCode(200).body(is("true"));
		 // datenbank licenskey cool => id==10
	    Response res = 
	            given()
	            .queryParam("licenskey", "Cool")
	            .contentType(MediaType.APPLICATION_JSON)
	            .when()
	            .get("contract");
	    		
	    		res.then().statusCode(200);
	    		
	    		List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
	    		Assertions.assertEquals( contractA.getId(), ctr.get(0).getId());
	    		Assertions.assertEquals( 1, ctr.size());
		
	      
		          
	}

	
	@Test
	@Order(7)
	public void DeleteContract() {
		
		 given()
	        .pathParam("id", contractA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FA)
	        .when()
	        .put("feature/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));
		 given()
	        .pathParam("id", contractA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FB)
	        .when()
	        .put("feature/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));
		 given()
		 	.pathParam("id", contractA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpA)
	        .when()
	        .put("IpNumber/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));
		 given()
		 	.pathParam("id", contractA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpB)
	        .when()
	        .put("IpNumber/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));
		
		//contractA.setId(2l); 
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(contractA)
		          .when()
		          .delete("contract")	
		          .then().statusCode(204);  
		        
     	Response res =
     	        given()
     	        .contentType(MediaType.APPLICATION_JSON)
     	        .when()
     	        .get("contract");
     	    	
     			res.then().statusCode(200);
     			List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
     			Assertions.assertEquals( 0, ctr.size());
	}

		@Test
		@Order(8)
		public void deleteall(){
			//Delete all
				companyOrm.deleteall();			
		}
				
}
	
	
	
	
	
	
	



