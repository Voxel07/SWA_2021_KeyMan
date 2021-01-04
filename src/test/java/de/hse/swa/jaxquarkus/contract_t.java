package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import de.hse.swa.jaxquarkus.step4.model.*;

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
public class contract_t{
	
	private static Contract contractA = new Contract("1.1.2020", "1.1.2021", "ver1","1234");
	private static Contract contractB = new Contract("2.2.2020", "2.2.2021", "ver2", "4321");
	private static Contract contractC = new Contract("3.3.2020", "3.3.2021", "ver1", "5678");
	
	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);
	private static User usrB = new User("Bemail", "Busername", "Bpassword", "Bfirst", "Blast",  false);	
	private static User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true);
		
	private static IpNumber IpA = new IpNumber("111.111.111.111");
	private static IpNumber IpB = new IpNumber("222.222.222.222");
	private static IpNumber IpC = new IpNumber("333.333.333.333");
	
	private static Feature FA = new Feature("1");
	private static Feature FB = new Feature("2");
	private static Feature FC = new Feature("3");
	
	@Test
	@Order(1)
	public void AddContract() {
		
		 given()
		 .queryParam("companyId", 1l)
		 .contentType(MediaType.APPLICATION_JSON)
		 .body(contractC)
		 .when()
		 .put("contract")
		 .then().statusCode(200).body(is("Contract added"));    	  	
	} 
	
	@Test
	@Order(2)
	public void addConnectionUserContract() {
		contractA.setId(1l);

		 given()
		 .queryParam("usrId", 1l)
		 .contentType(MediaType.APPLICATION_JSON)
		 .body(contractA)
		 .when()
		 .post("contract")
		 .then().statusCode(200).body(is(true));  	  	
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
		Assertions.assertEquals( contractB.getLicenskey(), ctr.get(1).getLicenskey());
		Assertions.assertEquals( contractC.getLicenskey(), ctr.get(2).getLicenskey());
		Assertions.assertEquals( 3, ctr.size());
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
        .queryParam("company_id", 1l)
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("contract");
		
		res.then().statusCode(200);
		
		List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
		Assertions.assertEquals( contractC.getLicenskey(), ctr.get(0).getLicenskey());
	}

	
	@Test
	@Order(6)
	public void UpdateContract() { 
		
		contractC.setId(10l);
		contractC.setLicenskey("Cool");
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(contractC)
		          .when()
		          .post("contract")		
		          .then().statusCode(204);   
		 // datenbank licenskey cool => id==10
	    Response res = 
	            given()
	            .queryParam("licenskey", "Cool")
	            .contentType(MediaType.APPLICATION_JSON)
	            .when()
	            .get("contract");
	    		
	    		res.then().statusCode(200);
	    		
	    		List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
	    		Assertions.assertEquals( contractC.getId(), ctr.get(0).getId());
	    		Assertions.assertEquals( 1, ctr.size());
		
	      
		          
	}

	
	@Test
	@Order(7)
	public void DeleteContract() {
		
		 given()
	        .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FA)//10
	        .when()
	        .put("feature/{id}")	
	        .then()
	        .statusCode(200).body(is("Feature added"));
		 given()
	        .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(FB)//10
	        .when()
	        .put("feature/{id}")	
	        .then()
	        .statusCode(200).body(is("Feature added"));
		 given()
		 	.pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpA)
	        .when()
	        .put("IpNumber/{id}")	
	        .then()
	        .statusCode(200).body(is("IP added"));
		 given()
		 	.pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpB)
	        .when()
	        .put("IpNumber/{id}")	
	        .then()
	        .statusCode(200).body(is("IP added"));
		
		contractA.setId(1l); 
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
     			Assertions.assertEquals( 2, ctr.size());
	}
	
}
	
	
	
	
	
	
	



