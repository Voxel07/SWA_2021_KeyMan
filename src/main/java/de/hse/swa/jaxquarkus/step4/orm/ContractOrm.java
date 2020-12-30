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
    
    public Contract getContractById(Long id) 
    {	
    	return em.find(Contract.class,id);
	}

	public List<Contract> getContractByNumber(String number) 
    {	
		TypedQuery<Contract> query = em.createQuery("SELECT FROM Contract WHERE number =: val1", Contract.class);
		return query.setParameter("val", number).getResultList();
	}

	public List<Contract> getContractByCompany(Long id) 
    {	
		TypedQuery<Contract> query = em.createQuery("SELECT FROM Contract WHERE company_id =: val1", Contract.class);
		return query.setParameter("val", id).getResultList();
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
    public String deleteContract(Contract contract) 
    { 	
		System.out.println("ContractOrm/deleteContract");
		if(Boolean.FALSE.equals(ipNumberOrm.removeAllIpsfromContract(contract))){
			return "removeIP";
		}
		if(Boolean.FALSE.equals(featureOrm.removeAllFeaturesfromContract(contract))){
			return "removeFeature";
		}
	
		 
		em.createQuery("DELETE FROM Contract WHERE id =: val")/*Ich bin Wichtig !!*/
		.setParameter("val", contract.getId())
		.executeUpdate();
		return "works";
    }    
       
    @Transactional
    public String addConnectionUserContract(User usr, Contract ctr) {
        usr.getContracts().add(ctr);
        ctr.getUsers().add(usr);
    	return "";
    }

	@Transactional
	public Boolean removeConnectionUserContract(Contract ctr, User usr){
		return	em.createQuery("DELETE FROM user_contract WHERE User_id =: val1 AND Contract_id =: val2")/*Ich bin Wichtig !!*/
		.setParameter("val1", usr.getId())
		.setParameter("val2", ctr.getId())
		.executeUpdate()==1;
	}

	//Sinvoll ??? ->
	@Transactional
	public Boolean removeAllConnectionUserContract(User usr){
		return	em.createQuery("DELETE FROM user_contract WHERE User_id =: val1")/*Ich bin Wichtig !!*/
		.setParameter("val1", usr.getId())
		.executeUpdate()==1;
	}

}

