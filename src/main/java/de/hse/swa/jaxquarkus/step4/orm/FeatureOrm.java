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
	 
	public List<Feature> getFeatures(){
		TypedQuery<Feature> query = em.createQuery("SELECT f FROM Feature f", Feature.class);
     	return query.getResultList();
	}

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
		System.out.println("FeatureORM/updateFeature"); 
 int test = 0;
 test =  em.createQuery("UPDATE Feature SET number =: val1 WHERE id =: val2")
			.setParameter("val1", feature.getNumber())
			.setParameter("val2", feature.getId())
			.executeUpdate();
 
			System.out.println(test);
 if(test == 1) {
	 return true;
 }
 return false;
	}
   
    @Transactional
    public Boolean addFeature(Long contractId, Feature f) {

		if(getContractFeatures(contractId).size() >=3){
			return false;
		}
		
		Contract c = em.find(Contract.class,contractId);
		c.getFeatures().add(f);
		f.setCrtF(c);

		em.persist(f);
		em.merge(c);
		return true;
	}

    @Transactional
    public Boolean removeFeature(Feature f) {
		return	em.createQuery("DELETE FROM Feature WHERE id =: val")/*Ich bin Wichtig !!*/
		.setParameter("val", f.getId())
		.executeUpdate()==1;
    }
    
    @Transactional
    public Boolean removeAllFeaturesfromContract(Contract c) {
		System.out.println("ContractOrm/removeAllFeaturesfromContract");
		if(!getContractFeatures(c.getId()).isEmpty()){
			return	em.createQuery("DELETE FROM Feature WHERE contract_id =: val")/*Ich bin Wichtig !!*/
			.setParameter("val", c.getId())
			.executeUpdate()!=0;
		}
		else{
			return true;
		}
    }
}
