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

import de.hse.swa.jaxquarkus.step4.model.*;

import de.hse.swa.jaxquarkus.step4.orm.ContractOrm;

@Path("/contracts")
public class ContractResource {
	
    @ApplicationScoped
    
    @Inject
    ContractOrm contractOrm;
    
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public List<Contract> getCompanys() 
    {
        return contractOrm.getContracts();
    }
   
    
    @GET
	@Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Contract getContract(@PathParam("id") Long id)
    {
        return contractOrm.getContract(id); 
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
    
    @POST
    @Path("/add/Ip/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addIp(@PathParam("id")Long crtId,IpNumber Ip) 
    {
    	return contractOrm.addIp(crtId, Ip);
    }
    
    @POST
    @Path("/add/Feature/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addFeature(@PathParam("id")Long crtId, Feature f) 
    {
    	return contractOrm.addFeature(crtId, f);
    }
    
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteContract(Contract contract) 
    {
    	contractOrm.deleteContract(contract);
    }
    
    @DELETE
    @Path("/remove/Ip")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removeIp(IpNumber Ip) 
    {
    	return contractOrm.removeIp(Ip);
    }
    
    @DELETE
    @Path("/remove/Feature")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removeFeature( Feature f) 
    {
    	return contractOrm.removeFeature(f);
    }
    
  
    
}