package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.jaxquarkus.step4.model.Phone;
import de.hse.swa.jaxquarkus.step4.model.User;

@ApplicationScoped
public class PhoneOrm{
	
	@Inject
	EntityManager em;

	public List<Phone> getPhones(){
		TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p", Phone.class);
     	return query.getResultList();
	}
	
	public List<Phone> getPhoneByNumber(String number) {
		System.out.println("PhoneORM/getPhoneByNumber");
		TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.number =:val", Phone.class);
		query.setParameter("val", number);
		return query.getResultList();
	}

    public List<Phone> getUserPhones(Long usrId){
		System.out.println("PhoneORM/getUserPhones");
    	TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE user_id = :val", Phone.class);
    	query.setParameter("val", usrId);
    	return query.getResultList();
    }
	
	@Transactional
	public Boolean updatePhone(Phone phone) {
		if(getPhoneByNumber(phone.getNumber()).isEmpty()){
			em.merge(phone);
			return true;
		}
		else{
			return false;
		}
	}
	
	@Transactional
    public Boolean addPhone(Long usrId, Phone p) {
		
		if(!getPhoneByNumber(p.getNumber()).isEmpty()) {
			return false;
		}
		if((getUserPhones(usrId).size() >= 2)) {
			return false;
		}
		
		User usr = em.find(User.class, usrId);
		
		
		usr.getPhones().add(p);
		//hat gefehlt
		p.setUsr(usr);
		em.persist(p);
		em.merge(usr);
		return true;
		
		
		
		
//    	Boolean duplicate = false;
//    	int anzNumber = 0; 
//    	String error = "Max anz erreicht";
//    	
//    	System.out.println("Aus der ORM: "+" ID: "+usrId+" number: "+p.getNumber()+" type: "+p.getType());  	
//    	//Pr�fen dass nur zwei nummern da sind
//		TypedQuery<Phone> query =em.createQuery("SELECT p FROM Phone p WHERE user_id = :val OR p.number = :val2", Phone.class);
//		query.setParameter("val", usrId);
//		query.setParameter("val2", p.getNumber());
//		
//		System.out.println("AnzResultsQuery: "+ query.getResultList().size());
//		
//		//Check all Numbers
//	
//		for(Phone elem : query.getResultList()) {
//			System.out.println("AktElement: "+ elem );
//			//if(elem.getNumber()==number) geht nicht aus Gr�nden 
//			//Check for duplicate entry and anzNumbers
//			if(elem.getNumber().equals(p.getNumber())) {
//				System.out.println("ComparingNumbers: " +elem.getNumber()+" to "+p.getNumber() );
//				duplicate = true;
//				error = "Doppelte Nr entdeckt bei User: "+elem.getId();
//				break;
//			}
//		}
//  
//		if(query.getResultList().size() <= 1 && duplicate==false) {	
//			System.out.println("If erreicht mit "+anzNumber+" dub? "+ duplicate);
//			User usr = new User();
//			
//			try {
//				usr = em.find(User.class, usrId);
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("Custom Exception UserOrm addPhone Find User: "+ e.toString());
//				return e.toString();
//			}
//			Phone ph = new Phone(p.getNumber(),p.getType());
//			usr.getPhones().add(ph);
//			//hat gefehlt
//			ph.setUsr(usr);
//			em.persist(usr);
//			return "Phone added";
//		}
//		else {
//			System.out.println(error);
//			return error;
//		}	
	}
    
    @Transactional
    public Boolean removePhoneFromUser(Phone p) {
		return	em.createQuery("DELETE FROM Phone WHERE number =: val")/*Ich bin Wichtig !!*/
		.setParameter("val", p.getNumber())
		.executeUpdate()==1;
	}

	@Transactional
    public Boolean removeAllPhonesFromUser(User u) {
		System.out.println("PhoneOrm/removeAllPhonesFromUser");
		if(!getUserPhones(u.getId()).isEmpty()){
			int anz = em.createQuery("DELETE FROM Phone WHERE user_id =: val")/*Ich bin Wichtig !!*/
			.setParameter("val", u.getId())
			.executeUpdate();
			return anz == 1 || anz ==2;
		}
		else{
			return true;
		}
    }


}

