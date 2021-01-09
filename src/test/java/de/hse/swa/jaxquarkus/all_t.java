package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import io.restassured.response.Response;
import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class all_t{
	
	@Inject
	ContractOrm contractOrm;
	@Inject
	UserOrm userOrm;
	@Inject
	CompanyOrm companyOrm;
	@Inject
	PhoneOrm phoneOrm;
	
	private static Contract contractA = new Contract("1.1.2020", "1.1.2021", "ver1", "1111");
	private static Contract contractB = new Contract("2.2.2020", "2.2.2021", "ver2", "2222");
	private static Contract contractC = new Contract("3.3.2020", "3.3.2021", "ver1", "3333");

	private static Company companyA = new Company("Aname", "Adepartment", "Astreet", 12345, "Astate", "Acountry");
	private static Company companyB = new Company("Bname", "Bdepartment", "Bstreet", 12345, "Bstate", "Bcountry");
	private static Company companyC = new Company("Cname", "Cdepartment", "Cstreet", 12345, "Cstate", "Ccountry");	
	
	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);
	private static User usrB = new User("Bemail", "Busername", "Bpassword", "Bfirst", "Blast",  false);	
	private static User usrC = new User("Cemail", "Cusername", "Cpassword", "Cfirst", "Clast", true);

	private static Phone phoneA = new Phone("Anumber", "Atype");  
	private static Phone phoneB = new Phone("Bnumber", "Btype");
	private static Phone phoneC = new Phone("Cnumber", "Ctype");
	
	private static IpNumber IpA = new IpNumber("111.111.111.111");
	private static IpNumber IpB = new IpNumber("222.222.222.22");
	private static IpNumber IpC = new IpNumber("333.333.333.33");
	
	private static Feature FA = new Feature("1");
	private static Feature FB = new Feature("2");
	private static Feature FC = new Feature("3");
	

	@Test
	@Order(1)
	public void companyDelete(){

		//Company
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(companyC)
		.when()
		.put("/company")		
		.then().statusCode(204);

		//User
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.pathParam("companyId", 1l)
		.body(usrC)
		.when()
		.put("/user/{companyId}")	
		.then().statusCode(200).body(is("true"));

		//Phones
		given()
		.pathParam("id", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.body(phoneA)
		.when()
		.put("/phone/{id}")	
		.then()
		.statusCode(200).body(is("true"));

		given()
		.pathParam("id", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.body(phoneB)
		.when()
		.put("/phone/{id}")	
		.then()
		.statusCode(200).body(is("true"));

		//Contract
		given()
		.pathParam("companyId", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.body(contractA)
		.when()
		.put("contract/{companyId}")
		.then().statusCode(200).body(is("Contract added"));   

		given()
		.pathParam("companyId", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.body(contractB)
		.when()
		.put("contract/{companyId}")
		.then().statusCode(200).body(is("Contract added"));   

		//Feature / Ip´s
		given()
        .pathParam("id", 1l)
        .contentType(MediaType.APPLICATION_JSON)
        .body(FA)//10
        .when()
        .put("feature/{id}")	
        .then()
		.statusCode(200).body(is("true"));

		given()
        .pathParam("id", 1l)
        .contentType(MediaType.APPLICATION_JSON)
        .body(FB)//10
        .when()
        .put("feature/{id}")	
        .then()
		.statusCode(200).body(is("true"));

		given()
        .pathParam("id", 2l)
        .contentType(MediaType.APPLICATION_JSON)
        .body(FB)//11
        .when()
        .put("feature/{id}")	
        .then()
		.statusCode(200).body(is("true"));

		given()
		.pathParam("id", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.body(IpA)
		.when()
		.put("IpNumber/{id}")	
		.then()
		.statusCode(200).body(is("true"));

	   given()
	   	.pathParam("id", 2l)
		.contentType(MediaType.APPLICATION_JSON)
		.body(IpB)
		.when()
		.put("IpNumber/{id}")	
		.then()
		.statusCode(200).body(is("true"));

		//connect user Contract
		contractA.setId(1l);
		given()
		.queryParam("usrId", 1l)
		.contentType(MediaType.APPLICATION_JSON)
		.body(contractA)
		.when()
		.post("contract")	
		.then()
		.statusCode(200).body(is("true"));

		//Delete Company
		companyA.setId(1l);
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(companyA)
		.when()
		.delete("/company")		
		.then().statusCode(204);  

	}
		

	@Test
	@Order(2)
	public void deleteall(){
  
		
	}
	
}