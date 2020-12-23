package de.hse.swa.jaxquarkus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.CompanyOrm;
import de.hse.swa.jaxquarkus.step4.orm.ContractOrm;
import de.hse.swa.jaxquarkus.step4.orm.UserOrm;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import javax.inject.Inject;


@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class all_t{
	
	@Inject
	ContractOrm contractOrm;
	@Inject
	UserOrm userOrm;
	@Inject
	CompanyOrm companyOrm;
	
	private static Contract contractA = new Contract("1.1.2020", "1.1.2021", "ver1","1234");
	private static Contract contractB = new Contract("2.2.2020", "2.2.2021", "ver2", "4321");
	private static Contract contractC = new Contract("3.3.2020", "3.3.2021", "ver1", "5678");

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
	public void SetID_weil_pfusch_und_so() {
		companyA.setId(1l);
		companyB.setId(2l);
		companyC.setId(10l);
		
		usrA.setId(1l);
		usrB.setId(2l);
		usrC.setId(10l);
		
		contractA.setId(1l);
		contractB.setId(2l);
		contractC.setId(10l);
		
		phoneA.setId(1l);
		phoneB.setId(2l);
		phoneC.setId(10l);
		
		IpA.setId(10l);
		IpB.setId(11l);
		IpC.setId(12l);
		
		FA.setId(10l);
		FB.setId(11l);
		FC.setId(12l);
	}
	
	@Test
	@Order(2)
	public void contractAddUser() {

    	contractC.getUsers().add(usrC);
    	contractC.getUsers().add(usrA);
    	usrC.getContracts().add(contractC);
    	usrA.getContracts().add(contractC);
    	contractOrm.updateContract(contractC);

	}
	
	@Test
	@Order(3)
	public void userAddPhone() {
		usrC.getPhones().add(phoneA);
		usrC.getPhones().add(phoneB);
		usrC.getPhones().add(phoneC);
		userOrm.updateUser(usrC);
		
	}
	@Test
	@Order(4)
	public void ContractAddStuff() {
		contractA.getFeatures().add(FA);
		contractA.getFeatures().add(FB);
		contractA.getFeatures().add(FC);
		contractA.getIpNumbers().add(IpA);
		contractA.getIpNumbers().add(IpB);
		contractA.getIpNumbers().add(IpC);
		contractOrm.updateContract(contractA);
	}
	
	@Test
	@Order(5)
	public void addUserToCompany() {
		companyA.getUsers().add(usrA);
		companyB.getUsers().add(usrB);
		companyB.getUsers().add(usrC);
		companyOrm.updateCompany(companyA);
		companyOrm.updateCompany(companyB);
	}
	@Test
	@Order(6)
	public void addContractToCompany() {
		companyA.getContracts().add(contractA);
		companyA.getContracts().add(contractB);
		companyB.getContracts().add(contractC);
		companyOrm.updateCompany(companyA);
		
	}
//	@Test
//	@Order(7)
//	public void removeUser() {
//		userOrm.deleteUser(usrC);
//	}
	

	//@Test
//	@Order(8)
//	public void removContract() {
//		contractOrm.deleteContract(contractC);
	//}
	
	@Test
	@Order(9)
	public void testAddPhone() {
		userOrm.addPhone(1L, phoneA);
	}
	// test contracts
	@Test
	@Order(10)
	public void testAddIp() {
//		contractOrm.addIp(1L, "444.444.444.444");
	}
	
	@Test
	@Order(11)
	public void testAddFeature() {
//		contractOrm.addFeature(2L, "33");
	}
	@Test
	@Order(12)
	public void testRemoveFeature() {
//		contractOrm.removeFeature(10L, FA);
	}	
	
	
		
}