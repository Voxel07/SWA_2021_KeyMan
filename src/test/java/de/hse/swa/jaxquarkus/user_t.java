package de.hse.swa.jaxquarkus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.CompanyOrm;

import java.util.Arrays;
import java.util.List;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class user_t {
	private static Company companyA = new Company("Aname", "Adepartment", "Astreet", 12345, "Astate", "Acountry");
	private static User usrA = new User("Aemail", "Ausername", "Apassword", "Afirst", "Alast", true);

	@Inject
	CompanyOrm companyOrm;

	@Test
	@Order(1)
	public void AddUser() {

		given().contentType(MediaType.APPLICATION_JSON).body(companyA).when().put("/company").then().statusCode(200)
				.body(is("Company hinzugefügt"));

		Response response = given().contentType(MediaType.APPLICATION_JSON).when().get("/company");

		response.then().statusCode(200);

		List<Company> companys = Arrays.asList(response.getBody().as(Company[].class));
		companyA.setId(companys.get(0).getId());

		response = given().pathParam("companyId", companyA.getId()).contentType(MediaType.APPLICATION_JSON).body(usrA)
				.when().put("/user/{companyId}");
		response.then().statusCode(200).body(is("" + companyA.getId()));

		response = given().contentType(MediaType.APPLICATION_JSON).when().get("/user");
		response.then().statusCode(200);

		List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
		usrA.setId(usrs.get(0).getId());

	}

	@Test
	@Order(2)
	public void getAnzUsers() {
		Response response = given().contentType(MediaType.APPLICATION_JSON).when().get("/user");
		response.then().statusCode(200);

		List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
		Assertions.assertEquals(usrA.getLastName(), usrs.get(0).getLastName());
		Assertions.assertEquals(1, usrs.size());
	}

	@Test
	@Order(3)
	public void GetUsers() {
		Response response = given().contentType(MediaType.APPLICATION_JSON).when().get("/user");
		response.then().statusCode(200);

		List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
		Assertions.assertEquals(usrA.getLastName(), usrs.get(0).getLastName());
	}

	@Test
	@Order(4)
	public void GetUser() {
		System.out.println("Test4 Beginn");
		Response response = given().queryParam("companyId", companyA.getId()).contentType(MediaType.APPLICATION_JSON)
				.when().get("/user");
		response.then().statusCode(200);
		System.out.println("UserResource/getUser/zurückImTest");

		User usr = response.getBody().as(User.class);
		Assertions.assertEquals(usrA.getLastName(), usr.getLastName());
		System.out.println("Test4 Ende");
	}

	@Test
	@Order(5)
	public void UpdateUser() {
		System.out.println("Test5 Beginn");
		usrA.setLastName("Hans");
		Response response = given().contentType(MediaType.APPLICATION_JSON).body(usrA).when().post("/user");
		response.then().statusCode(204);

		response = given().queryParam("companyId", companyA.getId()).contentType(MediaType.APPLICATION_JSON).when()
				.get("user");
		response.then().statusCode(200);

		User usr = response.getBody().as(User.class);
		Assertions.assertEquals("Hans", usr.getLastName());
		System.out.println("Test5 Ende");
	}

	@Test
	@Order(6)
	public void DeleteUser() {

		Phone phoneA = new Phone("Anumber", "Atype");
		Phone phoneB = new Phone("Bnumber", "Btype");
		// usrA.setId(5l);

		given().pathParam("id", usrA.getId()).contentType(MediaType.APPLICATION_JSON).body(phoneA).when()
				.put("/phone/{id}").then().statusCode(200).body(is("true"));

		given().pathParam("id", usrA.getId()).contentType(MediaType.APPLICATION_JSON).body(phoneB).when()
				.put("/phone/{id}").then().statusCode(200).body(is("true"));

		Response response = given().contentType(MediaType.APPLICATION_JSON).body(usrA).when().delete("/user");
		response.then().statusCode(204);

		response = given().contentType(MediaType.APPLICATION_JSON).when().get("/user");
		response.then().statusCode(200);

		List<User> usrs = Arrays.asList(response.getBody().as(User[].class));
		Assertions.assertEquals(0, usrs.size());
	}

	@Test
	@Order(7)
	public void deleteall() {
		// Delete all
		companyOrm.deleteall();
	}
	// User add connection to Company
}