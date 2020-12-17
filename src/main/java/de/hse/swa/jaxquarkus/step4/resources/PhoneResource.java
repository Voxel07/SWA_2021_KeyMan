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

@Path("/phones")
public class PhoneResource {
	
	@ApplicationScoped
	    
    @Inject
    PhoneOrm phoneOrm;

	@GET
    @Produces("application/json")
    @Consumes("application/json")
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
//	@GET
//	@Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Phone getPhoneById(@PathParam("id") Long id)
//    {
//        return phoneOrm.getPhoneById(id);
//    }
	
	@GET
	@Path("{usr_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List <Phone> getPhoneByUser(@PathParam("usr_id") Long usr_id)
    {
        return phoneOrm.getPhonesByUsers(usr_id);
    }
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addPhone(Phone phone) 
    { 	//System.out.println("aus resource");
		//phone.printPhone();
        phoneOrm.addPhone(phone);
    }
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public void updatePhone(Phone phone) 
    { 	
        phoneOrm.updatePhone(phone);
    }
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public void deletePhone(Phone phone) 
	{
		phoneOrm.removePhone(phone);
	}
}