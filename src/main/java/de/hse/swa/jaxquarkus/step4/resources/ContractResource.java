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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hse.swa.jaxquarkus.step4.model.*;

import de.hse.swa.jaxquarkus.step4.orm.*;

@Path("/contracts")
public class ContractResource {
	
    @ApplicationScoped

    @Inject
    ContractOrm contractOrm;
   
    @Inject
    FeatureOrm featureOrm;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Contract> getContract(@QueryParam("number") String number, @QueryParam("company_id") Long companyId)
    {   
        if(number != null){
          return contractOrm.getContractByNumber(number);
        } 
        else if(companyId!=null){
            return contractOrm.getContractByCompany(companyId);
        }
        else{  
            return  contractOrm.getContracts();
        }
    }
        
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addContract(Contract contract) 
    { 	
    	contractOrm.addContract(contract);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateContract(Contract contract) 
    {
		System.out.println("CTR aus resource: "+contract.getUsers());
    	contractOrm.updateContract(contract);
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteContract(Contract contract) 
    {
    	contractOrm.deleteContract(contract);
    }


  
    
}