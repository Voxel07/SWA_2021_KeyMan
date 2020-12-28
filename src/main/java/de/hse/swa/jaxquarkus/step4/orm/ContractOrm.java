package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.jaxquarkus.step4.model.Contract;
import de.hse.swa.jaxquarkus.step4.model.IpNumber;
import de.hse.swa.jaxquarkus.step4.model.Feature;

@ApplicationScoped
public class ContractOrm  {

    @Inject
    EntityManager em; 
    
    
    public List<Contract> getContracts() 
    {
   	 TypedQuery<Contract> query = em.createQuery("SELECT u FROM Contract u", Contract.class);
   	 return query.getResultList();
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
    public String addIp(Long contractId, IpNumber Ip) {
    	Boolean duplicate = false;
    	int anzNumber = 0; 
    	String error = "Max anz erreicht";
    	
    	System.out.println("Aus der ORM: "+" ID: "+contractId+" number: "+Ip);  	
    	//checkt ob weniger als 3 Ips da sind
		TypedQuery<IpNumber> query =em.createQuery("SELECT i FROM IpNumber i WHERE contract_Id =:val OR number = :val2", IpNumber.class);
		query.setParameter("val", contractId);
		query.setParameter("val2", Ip.getIpNumber());
		
		System.out.println("AnzResultsQuery: "+ query.getResultList().size());
		
		//Check all IpNumbers
	
		for(IpNumber elem : query.getResultList()) {
			System.out.println("AktElement: "+ elem );
			
			//Check for duplicate entry and anzNumbers
			if(elem.getIpNumber().equals(Ip.getIpNumber())) {
				System.out.println("ComparingNumbers: " +elem.getIpNumber()+" to "+Ip );
				duplicate = true;
				error = "Doppelte Nr entdeckt bei Contract: "+elem.getId();
				break;
			}
			
		}
		
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
			
			contract.getIpNumbers().add(Ip);
			Ip.setContract(contract);
			em.persist(contract);
			return "IP added";
		}
		else {
			System.out.println(error);
			return error;
		}	
	}
    
    @Transactional
    public String removeIp(IpNumber Ip) {
    	
    	try {
			em.remove(em.contains(Ip) ? Ip : em.merge(Ip));
		} catch (Exception e) {
			e.printStackTrace();
			return "Ip"+Ip.getIpNumber()+"removed";
		}
    	return "kaputt";
    }
    
    
    @Transactional
    public String addFeature(Long contractId, Feature f) {
    	int anzNumber = 0; 
    	String error = "Max anz erreicht";
    	
    	System.out.println("Aus der ORM: "+" ID: "+contractId+" number: "+f);  	
    	//checkt ob weniger als 3 Features da sind
		TypedQuery<Feature> query =em.createQuery("SELECT f FROM Feature f WHERE contract_Id =:val OR number = :val2", Feature.class);
		query.setParameter("val", contractId);
		query.setParameter("val2", f.getNumber());
		
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
			contract.getFeatures().add(f);
			f.setCrtF(contract);
			em.persist(contract);
			return "Feature added";
		}
		else {
			System.out.println(error);
			return error;
		}		
	}

    @Transactional
    public String removeFeature(Feature f) {
    	try {
			em.remove(em.contains(f) ? f : em.merge(f));
		} catch (Exception e) {
			e.printStackTrace();
			return "Feature"+f.getNumber()+"removed";
		}
    	return "kaputt";
    }
    
    @Transactional
    public List<Feature> getFeaturesByContract(Long contract_id) {
		TypedQuery<Feature> query = em.createQuery("SELECT f FROM Feature f WHERE f.contract_id =:val", Feature.class);
		query.setParameter("val", contract_id);
		//Hier muss noch abgefangen werden, wenn der Nutzer keine Telefonnummer hat
		return query.getResultList();
	}




}

