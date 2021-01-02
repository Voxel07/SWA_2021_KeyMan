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
	@Inject
	UserOrm userOrm;

    
    public Contract getContract(Long id) 
    {	
    	return em.find(Contract.class,id);
    }
	
	public List<Contract> getContracts(){
		TypedQuery<Contract> query = em.createQuery("SELECT u FROM Contract u", Contract.class);
     	return query.getResultList();
	}
	
	public List<Contract> getContractByLicenskey(String licenskey) {
		System.out.println("ContractORM/getContractByLicenskey");
		TypedQuery<Contract> query = em.createQuery("SELECT u FROM Contract u WHERE u.licenskey =:val", Contract.class);
		query.setParameter("val", licenskey);
		return query.getResultList();
	}

    public List<Contract> getContractByCompany(Long companyId){
		System.out.println("ContractORM/getContractByNumber");
    	TypedQuery<Contract> query = em.createQuery("SELECT u FROM Contract u WHERE company_id = :val", Contract.class);
    	query.setParameter("val", companyId);
    	return query.getResultList();
    }
     
	@Transactional
    public String addContract(Contract contract) {

    	if(!getContractByLicenskey(contract.getLicenskey()).isEmpty())
    		return "Doppelter Licenskey";
    	
			em.persist(contract);
			return "Contract added";
	
	}

	@Transactional
	public Boolean updateContract(Contract contract) {
		if(!getContractByLicenskey(contract.getLicenskey()).isEmpty())
    		return false;
    	
			em.merge(contract);
			return true;
	}
	
	
    @Transactional
    public Boolean addConnectionUserContract(Long usrId, Long ctrId) {
    	
    	Contract ctr = getContract(ctrId);
    	User usr = userOrm.getUser(usrId);
    	usr.getContracts().add(ctr);
    	ctr.getUsers().add(usr);
        updateContract(ctr);
        userOrm.updateUser(usr);
    	return true;
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
	public Boolean removeConnectionUserContract(Long ctrId, Long usrId){
		return	em.createQuery("DELETE FROM user_contract WHERE User_id =: val1 AND Contract_id =: val2")/*Ich bin Wichtig !!*/
		.setParameter("val1", usrId)
		.setParameter("val2", ctrId)
		.executeUpdate()==1;
	}

	//Sinvoll ??? ->
	@Transactional
	public Boolean removeAllConnectionUserContract(Long usrId){
		return	em.createQuery("DELETE FROM user_contract WHERE User_id =: val1")/*Ich bin Wichtig !!*/
		.setParameter("val1", usrId)
		.executeUpdate()==1;
	}

}

