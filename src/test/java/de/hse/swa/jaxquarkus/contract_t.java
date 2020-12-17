package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import de.hse.swa.jaxquarkus.step4.model.Contract;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.MediaType;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class contract_t{
	
	private static Contract contractA = new Contract("1.1.2020", "1.1.2021", "ver1","1234");
	private static Contract contractB = new Contract("2.2.2020", "2.2.2021", "ver2", "4321");
	private static Contract contractC = new Contract("3.3.2020", "3.3.2021", "ver1", "5678");
	
	@Test
	@Order(1)
	public void AddContract() {
		Response response = 
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(contractC)
		          .when()
		          .put("/contracts");		
		          response.then().statusCode(204);
		 
	} 
	
	
	@Test
	@Order(2)
	public void GetContracts() {
    	Response response =
        given()
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/contracts");
    	
        response
        .then()
        .statusCode(200);
        
	List<Contract> contracts = Arrays.asList(response.getBody().as(Contract[].class));
		Assertions.assertEquals( contractA.getEndDate(), contracts.get(0).getEndDate());
		Assertions.assertEquals( contractB.getEndDate(), contracts.get(1).getEndDate());
	}
	
	
	@Test
	@Order(3)
	public void GetContract() {
		Long id = 1l;
		Response response = 
        given()
        .pathParam("id", id)
        .contentType(MediaType.APPLICATION_JSON)
        .when()
        .get("/contracts/{id}");
		response.then().statusCode(200);
		Contract contract = response.getBody().as(Contract.class);
		Assertions.assertEquals( contractA.getEndDate(), contract.getEndDate());
	}

	
	@Test
	@Order(4)
	public void UpdateContract() { 
		
		contractC.setId(10l);
		contractC.setLicenskey("Cool");
		Response response = 
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(contractC)
		          .when()
		          .post("/contracts");		
		          response.then().statusCode(204);      
	}

	
	@Test
	@Order(5)
	public void DeleteContract() {
		
		contractA.setId(1l);
		Response response = 
		        given()
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(contractA)
		          .when()
		          .delete("/contracts");		
		          response.then().statusCode(204);  
	}
}