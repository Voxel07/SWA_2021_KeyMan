package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.CompanyOrm;
import de.hse.swa.jaxquarkus.step4.orm.IpNumberOrm;

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
	public void addIpNumberToContract(){
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(companyA)
			.when()
			.put("/company")
			.then().statusCode(204);
	
		 given()
		 .pathParam("companyId", 8l)
		 .contentType(MediaType.APPLICATION_JSON)
		 .body(contractA)
		 .when()
		 .put("contract/{companyId}")
		 .then().statusCode(200).body(is("Contract added"));    	
		 
		 given()
		 	.pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpA)
	        .when()
	        .put("IpNumber/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));

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
		 .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(IpB)
	        .when()
	        .put("IpNumber/{id}")	
	        .then()
			.statusCode(200).body(is("true"));

		given()
		.pathParam("id", 1l)
			.contentType(MediaType.APPLICATION_JSON)
			.body(IpC)
			.when()
			.put("IpNumber/{id}")	
			.then()
			.statusCode(200).body(is("true"));

		}
	@Test
	@Order(4)
	public void addIpNumberToContractToMannyNumbers(){
		
		 given()
		    .pathParam("id", 1l)
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
		.queryParam("usr_id", 1l)
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
		 IpC.setId(3l);
		 
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
		contractC.setId(1l);
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
		IpB.setId(2l);
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
		contractA.setId(1l);
	//	phoneOrm.removeAllIpNumberFromUser(usrC);
		 
		 given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(contractA)
	        .when()
	        .delete("/IpNumber/all")	
	        .then()
			.statusCode(200).body(is("true"));
			
			Response res = given()
			.queryParam("ctrId", contractC.getId())
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
