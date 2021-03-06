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

    public List<User> getUsers() {
        System.out.println("UserOrm/getUsers");

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public List<User> getUserById(Long id) {
        System.out.println("UserOrm/getUserById");

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE id =: val", User.class);
        query.setParameter("val", id);
        return query.getResultList();
    }

    public List<User> getUserByUsername(String username) {
        System.out.println("UserOrm/getUserByUsername");

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE username =: val", User.class);
        query.setParameter("val", username);
        return query.getResultList();
    }

    public List<User> getUserByCompany(Long companyId) {
        System.out.println("UserOrm/getUserByCompany");
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE companyId =: val1", User.class);
        query.setParameter("val1", companyId);
        System.out.println("UserOrm/getUserByCompany/nachQuery");

        return query.getResultList();
    }
    
    public List<User> getUserByContract(Long ctrIdU) {
        System.out.println("UserOrm/getUserContracts");
    	return contractOrm.getContract(ctrIdU).getUsers();
    }

    @Transactional
    public String addUser(User usr, Long companyId) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username =: val1 OR u.email =: val2",
                User.class);
        query.setParameter("val1", usr.getUsername());
        query.setParameter("val2", usr.getEmail());

        if (!query.getResultList().isEmpty()) {
            return "Nutzer bereits bekannt";
        }

        Company c = em.find(Company.class, companyId);
        c.getUsers().add(usr);
        usr.setCompanyU(c);
        em.persist(usr);
        em.merge(c);

        return "" + getUserByUsername(usr.getUsername()).get(0).getId();
    }

    @Transactional
    public void updateUser(User usr) {
        System.out.println("UserOrm/updateUser");
        System.out.println(usr);
        em.merge(usr);
        em.createQuery("UPDATE User SET isAdmin =: val WHERE id =: val2")
        .setParameter("val", usr.isAdmin())
        .setParameter("val2", usr.getId())
        .executeUpdate();
    }

    @Transactional
    public String deleteUser(User usr) {
        System.out.println("UserOrm/deleteUser");

        if (Boolean.FALSE.equals(phoneOrm.removeAllPhonesFromUser(usr))) {
            return "removePhone";
        }
        // sollte ohne gehen
        // contractOrm.removeAllConnectionUserContract(usr.getId());

        em.createQuery("DELETE FROM User WHERE id =: val").setParameter("val", usr.getId()).executeUpdate();
        return "works";
    }

    // Kompliziertere Querrys

    @Transactional
    public String loginUser(User usr) {

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :val", User.class);
        query.setParameter("val", usr.getUsername());
        // Falls kein User mit dem namen gefunden wurde
        if (query.getResultList().isEmpty()) {
            return "Kein nutzer mit diesen Namen gefunden";
        }
        if (query.getSingleResult().getPassword().equals(usr.getPassword())) {
            return "true";
        } else {
            return "Passwort passt nicht";
        }

    }
}