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

    public List<IpNumber> getContractIpNumbers(Long ctrId){
		System.out.println("IpNumerORM/getContractIpNumbers");
    	TypedQuery<IpNumber> query = em.createQuery("SELECT ip FROM IpNumber ip WHERE contract_id = :val", IpNumber.class);
    	query.setParameter("val", ctrId);
    	return query.getResultList();
    }

    public List<IpNumber> getIpNumbersNumber(String number){
		System.out.println("IpNumerORM/getIpNumbersNumber");
    	TypedQuery<IpNumber> query = em.createQuery("SELECT ip FROM IpNumber ip WHERE number = :val", IpNumber.class);
    	query.setParameter("val", number);
    	return query.getResultList();
    }

    @Transactional
	public Boolean updateIpNumber(IpNumber ipNumber) {
        if(getIpNumbersNumber(ipNumber.getIpNumber()).isEmpty()){
			em.merge(ipNumber);
			return true;
		}
		else{
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
    public Boolean removeAllIpsfromContract(Contract c) {
		System.out.println("ContractOrm/removeAllIpsfromContract");
		if(!getContractIpNumbers(c.getId()).isEmpty()){
			return	em.createQuery("DELETE FROM IpNumbers WHERE contract_id =: val")/*Ich bin Wichtig !!*/
			.setParameter("val", c.getId())
			.executeUpdate()==1;
		}
		else{
			return true;
		}
    }
}