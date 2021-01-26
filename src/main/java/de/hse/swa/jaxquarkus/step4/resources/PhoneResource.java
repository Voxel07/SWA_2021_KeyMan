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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import de.hse.swa.jaxquarkus.step4.orm.PhoneOrm;
import de.hse.swa.jaxquarkus.step4.model.Phone;
import de.hse.swa.jaxquarkus.step4.model.User;
@Path("/phone")
public class PhoneResource {
	
	@ApplicationScoped
	    
    @Inject
    PhoneOrm phoneOrm;

	@GET
	// @Path("teste")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Phone> getPhones(@QueryParam("number") String number, @QueryParam("usrId") Long usrId)
    {   
        if(number != null){
          return phoneOrm.getPhoneByNumber(number);
        } 
        else if(usrId!=null){
            return phoneOrm.getUserPhones(usrId);
        }
        else{  
            return  phoneOrm.getPhones();
        }
      
    }
   
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean addPhone(@PathParam("id") Long UserId, Phone p ) 
    {
        return phoneOrm.addPhone(UserId,p);
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Boolean updatePhone(Phone phone) 
    { 	
        System.out.println("PhoneResource/updatePhone");
        System.out.println(phone);
        return phoneOrm.updatePhone(phone);
    }

	@DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean removePhoneFromUser(Phone p) 
    {
        return phoneOrm.removePhoneFromUser(p);
    }
   
    @DELETE
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean removeAllPhonesFromUser(User u) 
    {
        return phoneOrm.removeAllPhonesFromUser(u);
    }
}