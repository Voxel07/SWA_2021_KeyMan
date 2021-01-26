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
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class ipNumber_t{
    private static IpNumber IpA = new IpNumber("111.111.111.111");
	private static IpNumber IpB = new IpNumber("222.222.222.222");
	private static IpNumber IpC = new IpNumber("333.333.333.333");
	private static IpNumber IpD = new IpNumber("444.444.444.444");

    private static Company companyA = new Company("Aname", "Adepartment", "Astreet", 12345, "Astate", "Acountry");
	
	private static Contract contractA = new Contract("1.1.2020", "1.1.2021", "ver1");
	private static Contract contractC = new Contract("3.3.2020", "3.3.2021", "ver1");
	
	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);

	@Inject
    CompanyOrm companyOrm;
	
	@Test
	@Order(1)
	public void addIpNumberToContract(){
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(companyA)
			.when()
			.put("/company")
			.then().statusCode(200)
			.body(is("Company hinzugefügt"));
	
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
		 .then().statusCode(200).body(is("" + companyA.getId()));    	
		 
		 Response res =
			        given()
			        .contentType(MediaType.APPLICATION_JSON)
			        .when()
			        .get("contract");
			    	
					res.then().statusCode(200);
					List<Contract> ctr = Arrays.asList(res.getBody().as(Contract[].class));
					contractA.setId(ctr.get(0).getId());	
					
		 given()
		 	.pathParam("id", contractA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpA)
	        .when()
	        .put("IpNumber/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));
		 
		  res = given()
					.queryParam("ctr_id", contractA.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.when()
					.get("IpNumber");	
					
					res.then().statusCode(200);
					List<IpNumber> IpNumber = Arrays.asList(res.getBody().as(IpNumber[].class));
					IpA.setId(IpNumber.get(0).getId());	
					
		 response = 
				 given()
				 .pathParam("companyId", companyA.getId())
				 .contentType(MediaType.APPLICATION_JSON)
				 .body(usrA)
				 .when()
				 .put("/user/{companyId}");		
				 response.then().statusCode(200).body(is("" + companyA.getId()));
				 
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
		}
	// nicht merh unique
//	@Test
//	@Order(2)
//	public void addIpNumberToContractDuplicate(){
//		
//		 given()
//		    .pathParam("id", 1l)
//	        .contentType(MediaType.APPLICATION_JSON)
//	        .body(IpA)
//	        .when()
//	        .put("IpNumber/{id}")	
//	        .then()
//	        .statusCode(200).body(is("true"));
//
//		}
	
	@Test
	@Order(3)
	public void addIpNumberToContract2(){

		 given()
		 .pathParam("id", contractA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpB)
	        .when()
	        .put("IpNumber/{id}")	
	        .then()
			.statusCode(200).body(is("true"));

		 
		  Response res = given()
					.queryParam("ctr_id", contractA.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.when()
					.get("IpNumber");	
					
					res.then().statusCode(200);
					
		given()
		.pathParam("id", contractA.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.body(IpC)
			.when()
			.put("IpNumber/{id}")	
			.then()
			.statusCode(200).body(is("true"));
		
		  res = given()
					.queryParam("ctr_id", contractA.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.when()
					.get("IpNumber");	
					
					res.then().statusCode(200);
					
					List<IpNumber> IpNumber = Arrays.asList(res.getBody().as(IpNumber[].class));
					IpB.setId(IpNumber.get(1).getId());	
					IpC.setId(IpNumber.get(2).getId());	
		

		}
	@Test
	@Order(4)
	public void addIpNumberToContractToMannyNumbers(){
		
		 given()
		    .pathParam("id", contractA.getId())
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpD)
	        .when()
	        .put("IpNumber/{id}")	
	        .then()
	        .statusCode(200).body(is("false"));

		}

	@Test
	@Order(5)
	public void getUserIpNumber() {
		Response res = given()
		.queryParam("usr_id", usrA.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("IpNumber");	
		
		res.then().statusCode(200);
		List<IpNumber> IpNumber = Arrays.asList(res.getBody().as(IpNumber[].class));
		Assertions.assertEquals( IpA.getIpNumber(), IpNumber.get(0).getIpNumber());
		Assertions.assertEquals( IpB.getIpNumber(), IpNumber.get(1).getIpNumber());
		Assertions.assertEquals( 3, IpNumber.size());
	}

	@Test
	@Order(6)
	public void removeIpNumber() {
		 //IpC.setId(3l);
		 
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpC)
	        .when()
	        .delete("IpNumber")	
	        .then()
	        .statusCode(200).body(is("true"));
	}

	@Test
	@Order(7)
	public void getUserIpNumber2() {
		//contractC.setId(1l);
		Response res = given()
		.queryParam("ctr_id", contractC.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("IpNumber");	
       
		res.then().statusCode(200);
		List<IpNumber> IpNumbers = Arrays.asList(res.getBody().as(IpNumber[].class));
		Assertions.assertEquals( IpA.getIpNumber(), IpNumbers.get(0).getIpNumber());
		Assertions.assertEquals( IpB.getIpNumber(), IpNumbers.get(1).getIpNumber());
		Assertions.assertEquals( 2, IpNumbers.size());
	}

	@Test
	@Order(8)
	public void updateIpNumber(){
		IpB.setIpNumber("9876543");
		IpB.getId();
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(IpB)
        .when()
		.post("/IpNumber")	
		.then()
		.statusCode(200).body(is("true"));	
		
		Response res = given()
		.queryParam("number", IpB.getIpNumber())
        .contentType(MediaType.APPLICATION_JSON)
        .when()
		.get("/IpNumber");	
       
		res.then().statusCode(200);
		List<IpNumber> IpNumbers = Arrays.asList(res.getBody().as(IpNumber[].class));
		Assertions.assertEquals( IpB.getIpNumber(), IpNumbers.get(0).getIpNumber());
		Assertions.assertEquals( 1, IpNumbers.size());
	
	}

	@Test
	@Order(9)
	public void removeAllIpNumbersFromContract() {
		//contractA.setId(1l);
	//	phoneOrm.removeAllIpNumberFromUser(usrC);
		 
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(contractA)
	        .when()
	        .delete("/IpNumber/all")	
	        .then()
			.statusCode(200).body(is("true"));
			
			Response res = given()
			.queryParam("ctrId", contractA.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.when()
			.get("/IpNumber");	
			res.then().statusCode(200);
			List<IpNumber> IpNumbers = Arrays.asList(res.getBody().as(IpNumber[].class));
			Assertions.assertEquals( 0, IpNumbers.size());
	}
	
	@Test
	@Order(10)
	public void deleteall(){
		//Delete all
			companyOrm.deleteall();			
	}
}
