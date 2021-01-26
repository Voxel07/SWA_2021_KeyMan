package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import de.hse.swa.jaxquarkus.step4.model.*;

@ApplicationScoped
public class PhoneOrm {

	@Inject
	EntityManager em;

	public List<Phone> getPhones() {
		TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p", Phone.class);
		return query.getResultList();
	}

	public List<Phone> getPhoneByNumber(String number) {
		System.out.println("PhoneORM/getPhoneByNumber");
		TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.number =:val", Phone.class);
		query.setParameter("val", number);
		return query.getResultList();
	}

	public List<Phone> getUserPhones(Long usrId) {
		System.out.println("PhoneORM/getUserPhones");
		TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE user_id = :val", Phone.class);
		query.setParameter("val", usrId);
		return query.getResultList();
	}

	@Transactional
	public void updatePhone(Phone phone) {
		em.createQuery("UPDATE Phone SET number =: val , type =: val1 WHERE id =: val2")
        .setParameter("val", phone.getNumber())
        .setParameter("val1", phone.getType())
        .setParameter("val2", phone.getId())
        .executeUpdate();
	}

	@Transactional
	public Boolean addPhone(Long usrId, Phone p) {

		if (!getPhoneByNumber(p.getNumber()).isEmpty()) {
			return false;
		}
		if ((getUserPhones(usrId).size() >= 2)) {
			return false;
		}

		User usr = em.find(User.class, usrId);

		usr.getPhones().add(p);
		// hat gefehlt
		p.setUsr(usr);
		em.persist(p);
		em.merge(usr);
		return true;

	}

	@Transactional
	public Boolean removePhoneFromUser(Phone p) {
		return em.createQuery("DELETE FROM Phone WHERE number =: val")
				.setParameter("val", p.getNumber()).executeUpdate() == 1;
	}

	@Transactional
	public Boolean removeAllPhonesFromUser(User u) {
		System.out.println("PhoneOrm/removeAllPhonesFromUser");
		if (!getUserPhones(u.getId()).isEmpty()) {
			int anz = em.createQuery("DELETE FROM Phone WHERE user_id =: val")
					.setParameter("val", u.getId()).executeUpdate();
			return anz == 1 || anz == 2;
		} else {
			return true;
		}
	}

}
