package de.hse.swa.jaxquarkus.step4.resources;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hse.swa.jaxquarkus.step4.model.Company;
import de.hse.swa.jaxquarkus.step4.model.User;
import de.hse.swa.jaxquarkus.step4.orm.CompanyOrm;

@Path("/companys")
public class CompanyResource {
	
    @ApplicationScoped
    
    @Inject
    CompanyOrm companyOrm;
    
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public List<Company> getCompanys() 
    {
        return companyOrm.getCompanys();
    }
   
    
    @GET
	@Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Company getCompany(@PathParam("id") Long id)
    {
        return companyOrm.getCompany(id); 
    }
    

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCompany(Company company) 
    { 	
        companyOrm.addCompany(company);
    }
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateCompany(Company company) 
    {
        companyOrm.updateCompany(company);
    }
    
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteCompany(Company company) 
    {
        companyOrm.deleteCompany(company);
    }
    
  
    
}