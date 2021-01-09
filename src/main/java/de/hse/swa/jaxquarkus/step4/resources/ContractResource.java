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

@Path("/contract")
public class ContractResource {
	
    @ApplicationScoped

    @Inject
    ContractOrm contractOrm;
   
    @Inject
    FeatureOrm featureOrm;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Contract> getContract(@QueryParam("licenskey") String licenskey, @QueryParam("company_id") Long companyId)
    {   
    	System.out.println("ContratResource/getContract");
        if(licenskey != null){
        	System.out.println("getContractByLicenskey");
          return contractOrm.getContractByLicenskey(licenskey);
        } 
        else if(companyId!=null){
        	System.out.println("getContractByCompany");
            return contractOrm.getContractByCompany(companyId);
        }
        else{  
        	System.out.println("getContracts");
            return  contractOrm.getContracts();
        }
    }
       
    @PUT
    @Path("{companyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addContract(Contract contract,@PathParam("companyId") Long companyId) 
    { 	
    	System.out.println("ContratResource/addContract");
    	return contractOrm.addContract(contract, companyId);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean updateContract(Contract ctr, @QueryParam("usrId") Long usrId) 
    {
    	System.out.println("ContratResource/updateContract");
    	if(usrId == null) {
		System.out.println("updateContract");
    	return contractOrm.updateContract(ctr);
    	}
    	else {
    	System.out.println("addConnectionUserContract");
    	return contractOrm.addConnectionUserContract(usrId, ctr.getId());
    	}
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteContract(Contract ctr, @QueryParam("usrId") Long usrId) 
    {
    	System.out.println("ContratResource/deleteContract");
    	contractOrm.deleteContract(ctr);
//    	if(usrId == null) {
//    		System.out.println("deleteContract");
//    		contractOrm.updateContract(ctr);
//    	}
//    	else if(ctr == null) {
//    		System.out.println("removeAllConnectionUserContract");
//    		contractOrm.removeAllConnectionUserContract(usrId);
//    	}
//    	else {
//    		System.out.println("removeConnectionUserContract");
//    		contractOrm.removeConnectionUserContract(ctr.getId() , usrId);
//    	}
    }


  
    
}