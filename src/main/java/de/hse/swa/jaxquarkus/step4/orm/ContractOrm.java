package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.UUID;

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
	@Inject
	CompanyOrm companyOrm;

    
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
		System.out.println("ContractORM/getContractByCompany");
    	TypedQuery<Contract> query = em.createQuery("SELECT u FROM Contract u WHERE company_id = :val", Contract.class);
    	query.setParameter("val", companyId);
    	return query.getResultList();
	}
    public List<Contract> getContractsByUser(Long usrIdC) {
        System.out.println("UserOrm/getUserContracts");
    	return userOrm.getUserById(usrIdC).get(0).getContracts();
    }

     // wenn keine Company existiert kann kein Contract erstellt werden => fehlermeldung fehlt 
	@Transactional
    public String addContract(Contract contract, Long companyId) {
		UUID key = UUID.randomUUID();
    	contract.setLicenskey(""+key);
   
		Company c = companyOrm.getCompany(companyId);
		c.getContracts().add(contract); 
		contract.setCompanyC(c);
		em.persist(contract); 
		em.merge(c);

		return ""+getContractByLicenskey(contract.getLicenskey()).get(0).getId();
	
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
    	System.out.println("ContractOrm/addConnectionUserContract");
    	Contract ctr = getContract(ctrId);
    	List<User> usr = userOrm.getUserById(usrId);
    	usr.get(0).getContracts().add(ctr);
    	ctr.getUsers().add(usr.get(0));
        updateContract(ctr);
        userOrm.updateUser(usr.get(0));
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
	
		 
		em.createQuery("DELETE FROM Contract WHERE id =: val")
		.setParameter("val", contract.getId())
		.executeUpdate();
		return "works";
    }    
    

	@Transactional
	public Boolean removeConnectionUserContract(Long ctrId, Long usrId){
		return	em.createQuery("DELETE FROM user_contract WHERE User_id =: val1 AND Contract_id =: val2")
		.setParameter("val1", usrId)
		.setParameter("val2", ctrId)
		.executeUpdate()==1;
	}

	//Quasi nutzlos
	@Transactional
	public Boolean removeAllConnectionUserContract(Long usrId){
		return	em.createQuery("DELETE FROM user_contract WHERE User_id =: val1")
		.setParameter("val1", usrId)
		.executeUpdate()==1;
	}

	@Transactional
	public Boolean removeAllConnectionContractUser(Long contractId){
		return	em.createQuery("DELETE FROM user_contract WHERE Contract_id =: val1")
		.setParameter("val1", contractId)
		.executeUpdate()==1;
	}

}

