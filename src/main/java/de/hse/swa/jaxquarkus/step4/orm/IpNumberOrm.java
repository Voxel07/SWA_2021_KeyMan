package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import de.hse.swa.jaxquarkus.step4.model.*;

@ApplicationScoped
public class IpNumberOrm {
    @Inject
	EntityManager em; 
	
	public List<IpNumber> getIpNumbers(){
		TypedQuery<IpNumber> query = em.createQuery("SELECT ip FROM IpNumber ip", IpNumber.class);
     	return query.getResultList();
	}

    public List<IpNumber> getContractIpNumbers(Long ctrId){
		System.out.println("IpNumerORM/getContractIpNumbers");
    	TypedQuery<IpNumber> query = em.createQuery("SELECT ip FROM IpNumber ip WHERE contract_id = :val", IpNumber.class);
    	query.setParameter("val", ctrId);
    	return query.getResultList();
    }

    public List<IpNumber> getIpNumbersByNumber(String number){
		System.out.println("IpNumerORM/getIpNumbersNumber");
    	TypedQuery<IpNumber> query = em.createQuery("SELECT ip FROM IpNumber ip WHERE number = :val", IpNumber.class);
    	query.setParameter("val", number);
    	return query.getResultList();
    }

    @Transactional
	public Boolean updateIpNumber(IpNumber ipNumber) {

        // if(getIpNumbersByNumber(ipNumber.getIpNumber()).isEmpty()){
		// 	em.merge(ipNumber);
		// 	return true;
		// }
		// else{
		// 	return false;
		// }
		if(getIpNumbersByNumber(ipNumber.getIpNumber()).isEmpty()){
			System.out.println("IpORM/updateIpNumber/wasempty");
			return em.createQuery("UPDATE IpNumber SET number =: val1 WHERE id =: val2")
			.setParameter("val1", ipNumber.getIpNumber())
			.setParameter("val2", ipNumber.getId())
			.executeUpdate()==1;
		}
		else{
			System.out.println("IpORM/updateIpNumber/waseNotEmpty");
			return false;
		}
	}
   
	@Transactional
    public Boolean addIp(Long contractId, IpNumber ip) {
    	if(getContractIpNumbers(contractId).size() >=3){
			return false;
		}
		
		Contract c = em.find(Contract.class,contractId);
		c.getIpNumbers().add(ip);
		ip.setCrtIP(c);

		em.persist(ip);
		em.merge(c);
		return true;
	}
    
    @Transactional
    public Boolean removeIp(IpNumber Ip) {
		return	em.createQuery("DELETE FROM IpNumber WHERE number =: val")/*Ich bin Wichtig !!*/
		.setParameter("val", Ip.getIpNumber())
		.executeUpdate()==1;
    }
    
    @Transactional
    public Boolean removeAllIpsfromContract(Contract c) {
		System.out.println("ContractOrm/removeAllIpsfromContract");
		if(!getContractIpNumbers(c.getId()).isEmpty()){
			return	em.createQuery("DELETE FROM IpNumber WHERE contract_id =: val")/*Ich bin Wichtig !!*/
			.setParameter("val", c.getId())
			.executeUpdate()!=0;
		}
		else{
			return true;
		}
    }
}