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


@Path("/IpNumber")
public class IpNumberResource {
    @ApplicationScoped
    @Inject
    IpNumberOrm ipNumberOrm;

    @GET
	// @Path("teste")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<IpNumber> getPhones(@QueryParam("number") String number, @QueryParam("ctrId") Long ctrId)
    {   
        if(number != null){
          return ipNumberOrm.getIpNumbersByNumber(number);
        } 
        else if(ctrId!=null){
            return ipNumberOrm.getContractIpNumbers(ctrId);
        }
        else{  
            return  ipNumberOrm.getIpNumbers();
        }
      
    }

    @POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Boolean updateIpNumber(IpNumber number) 
    { 	
       return ipNumberOrm.updateIpNumber(number);
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean addIp(@PathParam("id")Long crtId,IpNumber Ip) 
    {
    	return ipNumberOrm.addIp(crtId, Ip);
    }
        
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean removeIp(IpNumber Ip) 
    {
    	return ipNumberOrm.removeIp(Ip);
    }

    @DELETE
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean removeAllIpsfromContract( Contract ctr) 
    {
        return ipNumberOrm.removeAllIpsfromContract(ctr);
    }
}
