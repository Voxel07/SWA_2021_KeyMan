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
    public List<Feature> getFeature(@QueryParam("number") String number, @QueryParam("ctr_id") Long ctrId)
    {   
        if(number != null){
          return featureOrm.getFeatureByNumber(number);
        } 
        else if(ctrId!=null){
            return featureOrm.getContractFeatures(ctrId);
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
    public Boolean addFeature(@PathParam("id")Long crtId, Feature f) 
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
