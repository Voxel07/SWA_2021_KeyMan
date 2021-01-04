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
    public Boolean deleteCompany(Company company) 
    { 	

        //hier die Listen durchgehen und alles löschen !s
        //aktuell fehlt contract-user table. Wird nicht gelöscht
        //immer del funktionen der einzelnen Klassen aufrufen.
       if(!contractOrm.getContractByCompany(company.getId()).isEmpty()) 
       {
           em.createQuery("DELETE FROM Contract WHERE company_Id =: val1")
           .setParameter("val1", company.getId())
           .executeUpdate();
       }
        
        if(!userOrm.getUserByCompany(company.getId()).isEmpty()) 
    	{
            em.createQuery("DELETE FROM User WHERE company_Id =: val1")
            .setParameter("val1", company.getId())
            .executeUpdate();
        }


        
        return	em.createQuery("DELETE FROM Company WHERE id =: val1")
		.setParameter("val1", company.getId())
        .executeUpdate()==1;
        
    }

  
    
}