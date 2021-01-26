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
	Assertions.assertEquals( 1, companys.size());
	Assertions.assertEquals( companyA.getName(), companys.get(0).getName());

	companyA.setId(companys.get(0).getId());
		
		 given()
		 .pathParam("companyId", companyA.getId())
		 .contentType(MediaType.APPLICATION_JSON)
		 .body(contractA)
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
					Assertions.assertEquals(1, ctr.size());
					Assertions.assertEquals( contractA.getVersion(), ctr.get(0).getVersion());
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
					.queryParam("ctrId", contractA.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.when()
					.get("IpNumber");	
					
					res.then().statusCode(200);
					List<IpNumber> IpNumber = Arrays.asList(res.getBody().as(IpNumber[].class));
					Assertions.assertEquals(1, IpNumber.size());
					Assertions.assertEquals(IpA.getIpNumber(), IpNumber.get(0).getIpNumber());
					IpA.setId(IpNumber.get(0).getId());	
		}

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
		
		given()
		.pathParam("id", contractA.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.body(IpC)
			.when()
			.put("IpNumber/{id}")	
			.then()
			.statusCode(200).body(is("true"));
		
		  Response res = given()
			.queryParam("ctrId", contractA.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.when()
			.get("IpNumber");	
			
			res.then().statusCode(200);
			List<IpNumber> IpNumber = Arrays.asList(res.getBody().as(IpNumber[].class));
			Assertions.assertEquals(3, IpNumber.size());
			Assertions.assertEquals(IpA.getIpNumber(), IpNumber.get(0).getIpNumber());
			Assertions.assertEquals(IpB.getIpNumber(), IpNumber.get(1).getIpNumber());
			Assertions.assertEquals(IpC.getIpNumber(), IpNumber.get(2).getIpNumber());
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
	public void getContractIpNumber() {
		Response res = given()
		.queryParam("ctrId", contractA.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.when()
		.get("IpNumber");	
		
		res.then().statusCode(200);
		List<IpNumber> IpNumber = Arrays.asList(res.getBody().as(IpNumber[].class));
		Assertions.assertEquals( IpA.getIpNumber(), IpNumber.get(0).getIpNumber());
		Assertions.assertEquals( IpB.getIpNumber(), IpNumber.get(1).getIpNumber());
		Assertions.assertEquals( IpC.getIpNumber(), IpNumber.get(2).getIpNumber());
		Assertions.assertEquals( 3, IpNumber.size());
	}

	@Test
	@Order(6)
	public void removeIpNumber() {
 
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
	public void getContractIpNumber2() {
		Response res = given()
		.queryParam("ctrId", contractA.getId())
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
	
	}

	@Test
	@Order(9)
	public void removeAllIpNumbersFromContract() {
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
