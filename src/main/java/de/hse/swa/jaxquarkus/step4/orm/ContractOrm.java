package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.jaxquarkus.step4.model.*;

@ApplicationScoped
public class ContractOrm  {

    @Inject
	EntityManager em; 
	@Inject
	IpNumberOrm ipNumberOrm;
	@Inject
	FeatureOrm featureOrm;

    
    
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
    { 	
		
    	em.remove(em.contains(contract) ? contract : em.merge(contract));
    }    
    // @Transactional
    // public List<Feature> getFeaturesByContract(Long contract_id) {
	// 	TypedQuery<Feature> query = em.createQuery("SELECT f FROM Feature f WHERE f.contract_id =:val", Feature.class);
	// 	query.setParameter("val", contract_id);
	// 	//Hier muss noch abgefangen werden, wenn der Nutzer keine Telefonnummer hat
	// 	return query.getResultList();
	// }
    // @Transactional
    // public List<IpNumber> getIpNumberByContract(Long contract_id) {
    // 	System.out.println("ContractORM/getIpNumberByContract");
	// 	TypedQuery<IpNumber> query = em.createQuery("SELECT f FROM IpNumber f WHERE f.contract_id =:val", IpNumber.class);
	// 	query.setParameter("val", contract_id);
	// 	return query.getResultList();
	// }

	//Sinvoll ???
	@Transactional
	public Boolean removeContractFromUser(Contract ctr){
		return	em.createQuery("DELETE FROM Contracts WHERE id =: val")/*Ich bin Wichtig !!*/
		.setParameter("val", ctr.getId())
		.executeUpdate()==1;
	}
	@Transactional
	public Boolean removeAllContractsFromUser(User usr){


		return false;
	}




}

