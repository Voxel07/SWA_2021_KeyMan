package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.jaxquarkus.step4.model.*;



@ApplicationScoped
public class UserOrm {
    @Inject
    EntityManager em; 
    
    
    public int getAnz () {
    	TypedQuery<User> query = em.createQuery("SELECT count(e) FROM User e",User.class);
    	return query.getResultList().size();
    }
    
    public List<User> getUsers(){
   	 TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
   	 return  query.getResultList();
    }

    public User getUser(Long id) 
    {	
    	return em.find(User.class,id);
    }
    
    public User getUser(String username) {
    	TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username =:?", User.class);
    	query.setParameter(1, username);
    	return query.getSingleResult();
    }

    public User getUser (User usr) {
    	return em.find(User.class, usr.getId());
    }
    
    @Transactional
    public void addUser(User usr){
    	em.persist(usr);
    }
     
    @Transactional
    public void updateUser(User usr){     
    		em.merge(usr);   	
    }
     
    @Transactional
    public void deleteUser(User usr){ 	
    	em.remove(em.contains(usr) ? usr : em.merge(usr));
    }
 
    //Kompliziertere Querrys
    
    @Transactional
    public Boolean loginUser(User usr){
    	Boolean status = false;
    	TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :val", User.class);
    	query.setParameter("val", usr.getUsername());
    	//Falls kein User mit dem namen gefunden wurde
    	if(query.getResultList().size()<1) {
    		status = false;
    	}
    	else {
    		if(query.getSingleResult().getPassword() == usr.getPassword()) {
    			status = true;
    		}
    	}
    	return status; 
    }
    

    public List<Phone> getUserPhones(User usr){
    	TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE user_id = :val", Phone.class);
    	query.setParameter("val", usr.getId());
    	System.out.println("Ich war in der DB");
    	System.out.println(query.getResultList().size());
    	System.out.println(query.getResultList().get(0).toString());
    	return query.getResultList();
    }

    
    @Transactional
    public String addPhone(Long usrId, Phone p) {
    	Boolean duplicate = false;
    	int anzNumber = 0; 
    	String error = "Max anz erreicht";
    	
    	System.out.println("Aus der ORM: "+" ID: "+usrId+" number: "+p.getNumber()+" type: "+p.getType());  	
    	//Pr�fen dass nur zwei nummern da sind
		TypedQuery<Phone> query =em.createQuery("SELECT p FROM Phone p WHERE user_id = :val OR p.number = :val2", Phone.class);
		query.setParameter("val", usrId);
		query.setParameter("val2", p.getNumber());
		
		System.out.println("AnzResultsQuery: "+ query.getResultList().size());
		
		//Check all Numbers
	
		for(Phone elem : query.getResultList()) {
			System.out.println("AktElement: "+ elem );
			//if(elem.getNumber()==number) geht nicht aus Gr�nden 
			//Check for duplicate entry and anzNumbers
			if(elem.getNumber().equals(p.getNumber())) {
				System.out.println("ComparingNumbers: " +elem.getNumber()+" to "+p.getNumber() );
				duplicate = true;
				error = "Doppelte Nr entdeckt bei User: "+elem.getId();
				break;
			}
		}
  
		if(query.getResultList().size() <= 1 && duplicate==false) {	
			System.out.println("If erreicht mit "+anzNumber+" dub? "+ duplicate);
			User usr = new User();
			
			try {
				usr = em.find(User.class, usrId);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Custom Exception UserOrm addPhone Find User: "+ e.toString());
				return e.toString();
			}
			Phone ph = new Phone(p.getNumber(),p.getType());
			usr.getPhones().add(ph);
			//hat gefehlt
			ph.setUsr(usr);
			em.persist(usr);
			return "User added";
		}
		else {
			System.out.println(error);
			return error;
		}	
	}
    
    @Transactional
    public Boolean removePhone(Phone p) {
    	
    	try {
			em.remove(em.contains(p) ? p : em.merge(p));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	return true;
    }
    
    @Transactional
    public String addContract(User usr, Contract ctr) {
    	
    	return "";
    }
    
    @Transactional
    public String removeContract(User usr, Contract ctr) {
    	
    	return "";
    }
    
    
    @Transactional
    public List<Contract> getUserContracts(User usr){
    	TypedQuery<Contract> query = em.createQuery("SELECT c FROM user_contracts c WHERE c.User_id =:?", Contract.class);
    	query.setParameter(1, usr.getId());
    	return query.getResultList();
    }
    
    
   


}