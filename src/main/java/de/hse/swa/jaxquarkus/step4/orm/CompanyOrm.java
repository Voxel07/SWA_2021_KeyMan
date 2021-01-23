package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.resteasy.annotations.Query;

import de.hse.swa.jaxquarkus.step4.model.*;


@ApplicationScoped
public class CompanyOrm {
	
    @Inject
    EntityManager em; 
    @Inject
    ContractOrm contractOrm;
    @Inject
    UserOrm userOrm;
    
    public List<Company> getCompanys() 
    {
        TypedQuery<Company> query = em.createQuery("SELECT u FROM Company u", Company.class);
        List<Company> results = query.getResultList();
        return results;
    }

    public Company getCompany(Long id) 
    {	
    	return em.find(Company.class,id);
    }
    
    @Transactional
    public String addCompany(Company company)
    {
        try{
            em.persist(company);
        }catch(Exception e)
        {
            return ""+e;
        }  
        return "Company hinzugefügt";
    }

    @Transactional
    public void updateCompany(Company company)
    {
        em.merge(company);
    }
    
    @Transactional
    public Boolean deleteCompany(Company company) 
    { 	
        

        //hier die Listen durchgehen und alles löschen !s
        //aktuell fehlt contract-user table. Wird nicht gelöscht
        //immer del funktionen der einzelnen Klassen aufrufen.

        List<Contract> contracts = contractOrm.getContractByCompany(company.getId());
        if(!contracts.isEmpty()) 
        {
                for (Contract aktContract : contracts) {
                    contractOrm.deleteContract(aktContract);
                }
        }

        List<User> users = userOrm.getUserByCompany(company.getId());
        if(!users.isEmpty()) 
    	{
            for (User aktUser : users) {
                userOrm.deleteUser(aktUser);
            }
        }


        
        return	em.createQuery("DELETE FROM Company WHERE id =: val1")
        .setParameter("val1", company.getId())
        .executeUpdate()==1;
        
    }
    
    @Transactional
    public void deleteall() 
    
    { 	em.createQuery("DELETE FROM Phone")
        .executeUpdate();
        em.createQuery("DELETE FROM User")
        .executeUpdate();
        em.createQuery("DELETE FROM Contract")
        .executeUpdate();
        em.createQuery("DELETE FROM Company")
        .executeUpdate();
    }
  
    
}