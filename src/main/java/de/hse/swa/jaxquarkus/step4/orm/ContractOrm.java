package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.jaxquarkus.step4.model.Contract;
import de.hse.swa.jaxquarkus.step4.model.User;


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

  
    
}