package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.jaxquarkus.step4.model.*;

import javax.json.Json;


@ApplicationScoped
public class UserOrm {
    @Inject
    EntityManager em; 
    
    
    public int getAnz () {
    	TypedQuery<User> query = em.createQuery("SELECT count(e) FROM User e",User.class);
    	//System.out.println("anzquery: "+query.getResultList());
    	return query.getResultList().size();
    }
    
    public List<User> getUsers() 
    {
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
    //Neu
    public User getUser (User usr) {
    	return em.find(User.class, usr.getId());
    }
    
    @Transactional
    public void addUser(User usr)
    {
    	em.persist(usr);
    }
     
    @Transactional
    public void updateUser(User usr)
    {     
    		em.merge(usr);   	
    }
     
    @Transactional
    public void deleteUser(User usr) 
    { 	//Das ist suspekt
    	em.remove(em.contains(usr) ? usr : em.merge(usr));
    }
 
    //Kompliziertere Querrys
    @Transactional
    public Boolean loginUser(User usr){
    	Boolean status = false;
    	TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username =:?", User.class);
    	query.setParameter(1, usr.getUsername());
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
    @Transactional
    public List<Phone> getUserPhones(User usr){
    	TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.user_id =:?", Phone.class);
    	query.setParameter(1, usr.getId());
    	return query.getResultList();
    }
    //Fehlerfall muss noch abgefangen werden
    @Transactional
    public void addPhone(Long usrId, String number, String type) {
    	System.out.println("Aus der ORM: "+" ID: "+usrId+" number: "+number+"type: "+type);  	
    	//Prüfen dass nur zwei nummern da sind
		TypedQuery<Phone> query =em.createQuery("SELECT p FROM Phone p WHERE user_id =:val", Phone.class);
		query.setParameter("val", usrId);
		System.out.println("result: "+ query.getResultList().size());
		if(query.getResultList().size()<2) {	
			User usr = em.find(User.class, usrId);
			Phone ph = new Phone(number,type);
			usr.getPhones().add(ph);
			em.persist(usr);
			return;
		}
		
			
	}
    @Transactional
    public List<Contract> getUserContracts(User usr){
    	TypedQuery<Contract> query = em.createQuery("SELECT c FROM user_contracts c WHERE c.User_id =:?", Contract.class);
    	query.setParameter(1, usr.getId());
    	return query.getResultList();
    }
    
    
   


}