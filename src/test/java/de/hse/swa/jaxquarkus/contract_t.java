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
				 .contentType(MediaType.APPLICATION_JSON)
				 .body(contractC)
				 .when()
				 .put("contracts")
				 .then().statusCode(204);    	  	
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
				 .post("contracts")
				 .then().statusCode(204);    	  	
	} 
		
	@Test
	@Order(3)
	public void GetContracts() {
    	Response res =
        given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("contracts");
    	
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
        .get("contracts");
		
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
        .get("contracts");
		
		res.then().statusCode(200);
		
		List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
		Assertions.assertEquals( contractA.getLicenskey(), ctr.get(0).getLicenskey());
	}

	
//	@Test
//	@Order(4)
//	public void UpdateContract() { 
//		
//		contractC.setId(10l);
//		contractC.setLicenskey("Cool");
//		Response response = 
//		        given()
//		        .contentType(MediaType.APPLICATION_JSON)
//		        .body(contractC)
//		          .when()
//		          .post("/contract");		
//		          response.then().statusCode(204);      
//	}

	
//	@Test
//	@Order(5)
//	public void DeleteContract() {
//		
//		contractA.setId(1l);
//		Response response = 
//		        given()
//		        .contentType(MediaType.APPLICATION_JSON)
//		        .body(contractA)
//		          .when()
//		          .delete("/contracts");		
//		          response.then().statusCode(204);  
//	}
	
//	@Test
//	@Order(6)
//	public void AddFeature() {
//		
//		 given()
//		 .pathParam("id", 10l)
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .body(FA)
//	        .when()
//	        .post("feature/{id}")	
//	        .then()
//	        .statusCode(200).body(is("Feature added"));
//		
//		 given()
//		 .pathParam("id", 10l)
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .body(FB)
//	        .when()
//	        .post("feature/{id}")	
//	        .then()
//	        .statusCode(200).body(is("Feature added"));
//	}
//	@Test
//	@Order(7)
//	public void removeFeature() {
//		FB.setId(10l);
//		given()
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .body(FB)
//	        .when()
//	        .delete("feature")	
//	        .then()
//	        .statusCode(200).body(is("Feature "+FB.getNumber()+" removed"));
//		
//	}
//	@Test
//	@Order(8)
//	public void AddIp() {
//		 given()
//		 .pathParam("id", 10l)
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .body(IpA)
//	        .when()
//	        .post("contract/{id}")	
//	        .then()
//	        .statusCode(200).body(is("IP added"));
//		
//		 given()
//		 .pathParam("id", 10l)
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .body(IpB)
//	        .when()
//	        .post("contract/{id}")	
//	        .then()
//	        .statusCode(200).body(is("IP added"));
//	}
//	@Test
//	@Order(9)
//	public void removeIp() {
//		IpA.setId(10l);
//		given()
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .body(IpA)
//	        .when()
//	        .delete("IpNumber/Ip")	
//	        .then()
//			.statusCode(200).body(is("Ip "+FB.getNumber()+" removed"));
//	}
//	
//	@Test
//	@Order(10)
//	public void removeAllIpFromContract() {
//		IpA.setId(10l);
//		contractC.setId(10l);
//		
//		given()
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .body(contractC)
//	        .when()
//	        .delete("/contracts")	
//	        .then()
//			.statusCode(200).body(is("Ips removed"));
//	}
//
//	@Test
//	@Order(11)
//	public void removeAllFeaturesFromContract() {
//		FB.setId(10l);
//		contractC.setId(10l);
//		
//		given()
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .body(contractC)
//	        .when()
//	        .delete("/contracts")	
//	        .then()
//	        .statusCode(200).body(is("true"));
//	}
}
	
	
	
	
	
	
	



