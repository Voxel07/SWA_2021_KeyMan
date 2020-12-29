package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import de.hse.swa.jaxquarkus.step4.model.*;

@ApplicationScoped
public class FeatureOrm {
    @Inject
    EntityManager em; 

    public List<Feature> getContractFeatures(Long ctrId){
		System.out.println("FeatureORM/getContractFeatures");
    	TypedQuery<Feature> query = em.createQuery("SELECT f FROM Feature f WHERE contract_id = :val", Feature.class);
    	query.setParameter("val", ctrId);
    	return query.getResultList();
    }
    public List<Feature> getFeatureByNumber(String ftr){
		System.out.println("FeatureORM/getFeatureByNumber");
    	TypedQuery<Feature> query = em.createQuery("SELECT f FROM Feature f WHERE number = :val", Feature.class);
    	query.setParameter("val", ftr);
    	return query.getResultList();
    }

    @Transactional
	public Boolean updateFeature(Feature feature) {
        if(getFeatureByNumber(feature.getNumber()).isEmpty()){
			em.merge(feature);
			return true;
		}
		else{
			return false;
		}
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
    public Boolean removeAllFeaturesfromContract(Contract c) {
		System.out.println("ContractOrm/removeAllFeaturesfromContract");
		if(!getContractFeatures(c.getId()).isEmpty()){
			return	em.createQuery("DELETE FROM Features WHERE contract_id =: val")/*Ich bin Wichtig !!*/
			.setParameter("val", c.getId())
			.executeUpdate()==1;
		}
		else{
			return true;
		}
    }
}
