package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.jaxquarkus.step4.model.Company;
import de.hse.swa.jaxquarkus.step4.model.Contract;
import de.hse.swa.jaxquarkus.step4.model.Phone;
import de.hse.swa.jaxquarkus.step4.model.User;
import de.hse.swa.jaxquarkus.step4.model.IpNumbers;
import de.hse.swa.jaxquarkus.step4.model.Feature;

@ApplicationScoped
public class ContractOrm  {

    @Inject
    EntityManager em; 
    
    
    public List<Contract> getContracts() 
    {
   	 TypedQuery<Contract> query = em.createQuery("SELECT u FROM Contract u", Contract.class);
   	 List<Contract> results = query.getResultList();
   	 return results;
    }

    
    public Contract getContract(Long id) 
    {	
    	return em.find(Contract.class,id);
    }
    
    
    @Transactional
    public void addContract(Contract contract)
    {
    	em.persist(contract);
    }
    
    
    @Transactional
    public void updateContract(Contract contract)
    {
		em.merge(contract);
    }
    
    
    @Transactional
    public void deleteContract(Contract contract) 
    { 	//Das ist suspekt
    	em.remove(em.contains(contract) ? contract : em.merge(contract));
    }

    @Transactional
    public void addIpContract(Contract contract) 
    { 	//Das ist suspekt
    	
    }

    public String addIp(Long contractId, String number) {
    	Boolean duplicate = false;
    	int anzNumber = 0; 
    	String error = "Max anz erreicht";
    	
    	System.out.println("Aus der ORM: "+" ID: "+contractId+" number: "+number);  	
    	//checkt ob weniger als 3 Ips da sind
		TypedQuery<IpNumbers> query =em.createQuery("SELECT i FROM IpNumbers p WHERE contract_Id =:val OR i.number = :val2", IpNumbers.class);
		query.setParameter("val", contractId);
		query.setParameter("val2", number);
		
		System.out.println("AnzResultsQuery: "+ query.getResultList().size());
		
		//Check all IpNumbers
	
		for(IpNumbers elem : query.getResultList()) {
			System.out.println("AktElement: "+ elem );
			
			//Check for duplicate entry and anzNumbers
			if(elem.getNumber().equals(number)) {
				System.out.println("ComparingNumbers: " +elem.getNumber()+" to "+number );
				duplicate = true;
				error = "Doppelte Nr entdeckt bei User: "+elem.getId();
				break;
			}
			
		}
	
			  
		if(query.getResultList().size() <= 1 && duplicate==false) {	
			System.out.println("If erreicht mit "+anzNumber+" dub? "+ duplicate);
			Contract contract = new Contract();
			
			try {
				contract = em.find(Contract.class, contractId);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Custom Exception ContractOrm addIp Find Contract: "+ e.toString());
				return e.toString();
			}
			IpNumbers ip = new IpNumbers(number);
			contract.getIpNumbers().add(ip);
			em.persist(contract);
			return "Contract added";
		}
		else {
			System.out.println(error);
			return error;
		}
		
			
	}
    
    public String addFeature(Long contractId, String number) {
    	int anzNumber = 0; 
    	String error = "Max anz erreicht";
    	
    	System.out.println("Aus der ORM: "+" ID: "+contractId+" number: "+number);  	
    	//checkt ob weniger als 3 Features da sind
		TypedQuery<Feature> query =em.createQuery("SELECT i FROM Feature p WHERE contract_Id =:val OR i.number = :val2", Feature.class);
		query.setParameter("val", contractId);
		query.setParameter("val2", number);
		
		System.out.println("AnzResultsQuery: "+ query.getResultList().size());
		
		//Check all IpNumbers
			  
		if(query.getResultList().size() <= 1) {	
			System.out.println("If erreicht mit "+anzNumber);
			Contract contract = new Contract();
			
			try {
				contract = em.find(Contract.class, contractId);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Custom Exception ContractOrm addFeature Find Contract: "+ e.toString());
				return e.toString();
			}
			Feature f = new Feature(number);
			contract.getFeatures().add(f);
			em.persist(contract);
			return "Contract added";
		}
		else {
			System.out.println(error);
			return error;
		}
		
			
	}
    
}