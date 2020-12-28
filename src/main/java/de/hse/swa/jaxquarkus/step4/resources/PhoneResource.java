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
import de.hse.swa.jaxquarkus.step4.orm.PhoneOrm;
import de.hse.swa.jaxquarkus.step4.model.Phone;
import de.hse.swa.jaxquarkus.step4.model.User;

@Path("/phones")
public class PhoneResource {
	
	@ApplicationScoped
	    
    @Inject
    PhoneOrm phoneOrm;

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public List<Phone> getPhones() {
		 
		 return phoneOrm.getPhones();
	 }
	
	@GET
	@Path("{number}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Phone getPhoneByNumber(@PathParam("number") String number)
    {
        return phoneOrm.getPhoneByNumber(number);
    }

	@GET
	@Path("{usr_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List <Phone> getPhoneByUser(@PathParam("usr_id") Long usrId)
    {
        return phoneOrm.getUserPhones(usrId);
    }
	       
    @POST
    @Path("/add/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePhone(@PathParam("id") Long UserId, Phone p) 
    {
    	
        return phoneOrm.addPhone(UserId,p);
    }

	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public void updatePhone(Phone phone) 
    { 	
        phoneOrm.updatePhone(phone);
    }

	@DELETE
    @Path("/remove")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean removePhone(Phone p) 
    {
        return phoneOrm.removePhone(p);
    }
}