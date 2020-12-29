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
    @Inject
    PhoneOrm phoneOrm;
    @Inject
    ContractOrm contractOrm;
    
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
        System.out.println("UserOrm/deleteUser");
        System.out.println(usr.toString());
        String fehler ="";

      
        if(Boolean.FALSE.equals(phoneOrm.removeAllPhonesFromUser(usr))){
         fehler = "removePhone";
        }
        else if(Boolean.FALSE.equals(contractOrm.removeAllContractsFromUser(usr))){

        }
        else{
            System.out.println("wroks");
        }
      
        em.createQuery("DELETE FROM User WHERE id =: val")/*Ich bin Wichtig !!*/
        .setParameter("val", usr.getId())
        .executeUpdate();
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