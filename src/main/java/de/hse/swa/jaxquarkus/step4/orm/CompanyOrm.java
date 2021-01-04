package de.hse.swa.jaxquarkus.step4.orm;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import de.hse.swa.jaxquarkus.step4.model.Company;


@ApplicationScoped
public class CompanyOrm {
    @Inject
    EntityManager em; 
    
    
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
    public void addCompany(Company company)
    {
    	em.persist(company);
    }
    
    
    @Transactional
    public void updateCompany(Company company)
    {
    		em.merge(company);
    }
    
    
    @Transactional
    public void deleteCompany(Company company) 
    { 	
    	em.remove(em.contains(company) ? company : em.merge(company));
    }

  
    
}