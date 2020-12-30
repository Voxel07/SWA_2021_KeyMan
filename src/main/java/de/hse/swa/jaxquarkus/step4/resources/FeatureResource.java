package de.hse.swa.jaxquarkus.step4.resources;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.*;


@Path("/feature")
public class FeatureResource {
    @ApplicationScoped
    @Inject
    FeatureOrm featureOrm;

    @GET
	// @Path("teste")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Feature> getPhones(@QueryParam("number") String number, @QueryParam("usr_id") Long ftrId)
    {   
        if(number != null){
          return featureOrm.getFeatureByNumber(number);
        } 
        else if(ftrId!=null){
            return featureOrm.getContractFeatures(ftrId);
        }
        else{  
            return  featureOrm.getFeatures();
        }
    }
  
    
    @POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Boolean updateFeature(Feature number) 
    { 	
       return featureOrm.updateFeature(number);
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addFeature(@PathParam("id")Long crtId, Feature f) 
    {
    	return featureOrm.addFeature(crtId, f);
    }
        
    @DELETE
   // @Path("/remove/Feature")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean removeFeature( Feature f) 
    {
    	return featureOrm.removeFeature(f);
    }
  
    @DELETE
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean removeAllFeaturesfromContract( Contract ctr) 
    {
        return featureOrm.removeAllFeaturesfromContract(ctr);
    }
    
}
