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
    public String addIp(Long contractId, String Ipnumber) {
    	Boolean duplicate = false;
    	int anzNumber = 0; 
    	String error = "Max anz erreicht";
    	
    	System.out.println("Aus der ORM: "+" ID: "+contractId+" number: "+Ipnumber);  	
    	//checkt ob weniger als 3 Ips da sind
		TypedQuery<IpNumbers> query =em.createQuery("SELECT i FROM IpNumbers i WHERE contract_Id =:val OR number = :val2", IpNumbers.class);
		query.setParameter("val", contractId);
		query.setParameter("val2", Ipnumber);
		
		System.out.println("AnzResultsQuery: "+ query.getResultList().size());
		
		//Check all IpNumbers
	
		for(IpNumbers elem : query.getResultList()) {
			System.out.println("AktElement: "+ elem );
			
			//Check for duplicate entry and anzNumbers
			if(elem.getNumber().equals(Ipnumber)) {
				System.out.println("ComparingNumbers: " +elem.getNumber()+" to "+Ipnumber );
				duplicate = true;
				error = "Doppelte Nr entdeckt bei User: "+elem.getId();
				break;
			}
			
		}
	// add Ip nur wenn schon1 da ist, oder leg ich auch die erste an
	// 
			  
		if(query.getResultList().size() <= 2 && duplicate==false) {	
			System.out.println("If erreicht mit "+anzNumber+" dub? "+ duplicate);
			Contract contract = new Contract();
			
			try {
				contract = em.find(Contract.class, contractId);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Custom Exception ContractOrm addIp Find Contract: "+ e.toString());
				return e.toString();
			}
			IpNumbers ip = new IpNumbers(Ipnumber);
			contract.getIpNumbers().add(ip);
			em.persist(contract);
			return "Contract added";
		}
		else {
			System.out.println(error);
			return error;
		}
		
			
	}
    
    @Transactional
    public String addFeature(Long contractId, String Fnumber) {
    	int anzNumber = 0; 
    	String error = "Max anz erreicht";
    	
    	System.out.println("Aus der ORM: "+" ID: "+contractId+" number: "+Fnumber);  	
    	//checkt ob weniger als 3 Features da sind
		TypedQuery<Feature> query =em.createQuery("SELECT f FROM Feature f WHERE contract_Id =:val OR number = :val2", Feature.class);
		query.setParameter("val", contractId);
		query.setParameter("val2", Fnumber);
		
		System.out.println("AnzResultsQuery: "+ query.getResultList().size());
		
		//Check all IpNumbers
			  
		if(query.getResultList().size() <= 2) {	
			System.out.println("If erreicht mit "+anzNumber);
			Contract contract = new Contract();
			
			try {
				contract = em.find(Contract.class, contractId);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Custom Exception ContractOrm addFeature Find Contract: "+ e.toString());
				return e.toString();
			}
			Feature f = new Feature(Fnumber);
			contract.getFeatures().add(f);
			em.persist(contract);
			return "Contract added";
		}
		else {
			System.out.println(error);
			return error;
		}
		
			
	}
    
    @Transactional
    public List<Contract> getUsersContract(User usr){
    	TypedQuery<Contract> query = em.createQuery("SELECT c FROM Contracts c WHERE c.user_id =:?", Contract.class);
    	query.setParameter(1, usr.getId());
    	return query.getResultList();
    }

    @Transactional
    public String removeFeature(Long contractId, Feature f) {
    	String error = "keine Features";
    	System.out.println("Aus der ORM: "+" ID: "+contractId+" number: "+f);  	
    	//checkt ob weniger als 3 Features da sind
		TypedQuery<Feature> query =em.createQuery("SELECT f FROM Feature f WHERE contract_Id =:val OR number = :val2", Feature.class);
		query.setParameter("val", contractId);
		query.setParameter("val2", f);
		
		System.out.println("AnzResultsQuery: "+ query.getResultList().size());
		
		if(query.getResultList().size() >= 1) {	
			em.remove(f);
			error = "erfolgreich entfernt";
		}
		else {
			System.out.println(error);
		}
    	return error;
    }
    @Transactional
    public List<Feature> getFeaturesByContract(Long contract_id) {
		TypedQuery<Feature> query = em.createQuery("SELECT f FROM Feature f WHERE f.contract_id =:val", Feature.class);
		query.setParameter("val", contract_id);
		//Hier muss noch abgefangen werden, wenn der Nutzer keine Telefonnummer hat
		return query.getResultList();
	}




}

