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
	public void addPhoneToUser(){
		
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(companyA)
			.when()
			.put("/company")
			.then().statusCode(204);
		
		given()
		 .pathParam("companyId", 1l)
		 .contentType(MediaType.APPLICATION_JSON)
		 .body(usrA)
		 .when()
		 .put("/user/{companyId}")	
		 .then().statusCode(200).body(is("true"));
		
		 given()
		 	.pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneA)
	        .when()
	        .put("/phone/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));

		}
	
	@Test
	@Order(2)
	public void addPhoneToUserDuplicate(){
		
		 given()
		    .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneA)
	        .when()
	        .put("/phone/{id}")	
	        .then()
	        .statusCode(200).body(is("false"));

		}
	
	@Test
	@Order(3)
	public void addPhoneToUser2(){

		 given()
		 .pathParam("id", 1l)
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(phoneB)
	        .when()
	        .put("/phone/{id}")	
	        .then()
	        .statusCode(200).body(is("true"));

		}
	@Test
	@Order(4)
	public void addPhoneToUserToMannyNumbers(){
		
		 given()
		    .pathParam("id", 1l)
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
		.queryParam("usr_id", 1l)
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
		 phoneB.setId(1l);
		 
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
		phoneB.setId(2l);
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
		phoneB.setId(2l);
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
		phoneB.setId(1l);
		usrC.setId(1l);
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