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
				error = "Doppelte Nr entdeckt bei Contract: "+elem.getCrtIP().getId();
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
			Ip.setCrtIP(contract);
			em.persist(contract);
			return "IP added";
		}
		else {
			System.out.println(error);
			return error;
		}	
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