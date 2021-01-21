package de.hse.swa.jaxquarkus.step4.resources;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import de.hse.swa.jaxquarkus.step4.model.Company;
import de.hse.swa.jaxquarkus.step4.orm.*;
import java.util.ArrayList;

import javax.ws.rs.QueryParam;

@Path("/company")
public class CompanyResource {
	
    @ApplicationScoped
    
    @Inject
    CompanyOrm companyOrm;
    @Inject
    UserOrm userOrm;
    @Inject
    ContractOrm contractOrm;
    
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public List<Company> getCompanys( 	@QueryParam("usrId") Long usrId,
                                        @QueryParam("ctrId") Long ctrId  )                         
    {
        List<Company> pfusch = new ArrayList<>();
        System.out.println("CompanyResource/getCompanys");

        if(usrId != null){
        System.out.println("CompanyResource/getUserById");

            pfusch.add(userOrm.getUserById(usrId).get(0).getCompanyU());
            return pfusch;
        }
        else if(ctrId != null){
        System.out.println("CompanyResource/getContract");

            pfusch.add(contractOrm.getContract(ctrId).getCompanyC());
            return pfusch;
        }
        else{

            return companyOrm.getCompanys();
        }
    }
   
    
    @GET
	@Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Company getCompany(@PathParam("id") Long id)
    {
    	System.out.println("CompanyResource/getCompany");
        return companyOrm.getCompany(id); 
    }
    

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCompany(Company company) 
    { 	
    	System.out.println("CompanyResource/addCompany");
        companyOrm.addCompany(company);
    }
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateCompany(Company company) 
    {
    	System.out.println("CompanyResource/updateCompany");
        companyOrm.updateCompany(company);
    }
    
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteCompany(Company company) //boolean?
    {
    	System.out.println("CompanyResource/deleteCompany");
        companyOrm.deleteCompany(company);
    }
    
//    @DELETE
//    @Path("/delete")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void deleteall() 
//    {
//    	System.out.println("CompanyResource/deleteall");
//        companyOrm.deleteall();
//    }
    
  
    
}