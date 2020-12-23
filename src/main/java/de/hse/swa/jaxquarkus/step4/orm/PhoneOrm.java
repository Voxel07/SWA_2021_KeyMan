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
	public Phone getPhoneByNumber(String number) {
		TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.number =:val", Phone.class);
		query.setParameter("val", number);
		return query.getSingleResult();
	}
//	public Phone getPhoneById(Long id) {
//		TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.id =:val", Phone.class);
//		query.setParameter("val", id);
//		return query.getSingleResult();
//	}
	public List<Phone> getPhonesByUsers(Long usr_id) {
		TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE user_id =:val", Phone.class);
		query.setParameter("val", usr_id);
		//Hier muss noch abgefangen werden, wenn der Nutzer keine Telefonnummer hat
		return query.getResultList();
	}
	
	@Transactional
	public void updatePhone(Phone phone) {
		em.merge(phone);
	}
	@Transactional
	public void removePhone(Phone phone) {
		em.remove(em.contains(phone) ? phone : em.merge(phone));
//		em.remove(phone);
	}
	@Transactional
	public void addPhone(Phone phone){
//		TypedQuery<Phone> query =em.createQuery("SELECT p FROM Phone p WHERE p.usr =:val", Phone.class);
//		query.setParameter("val", phone.getUser());
//		System.out.println("Anz =" + query.getResultList().size());
//		if(query.getResultList().size()<=2) {	
			em.persist(phone);
//			return true;
//		}
//		else return false;
//			
		}
	
}

