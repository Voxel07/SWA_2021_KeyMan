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

import de.hse.swa.jaxquarkus.step4.model.User;
import de.hse.swa.jaxquarkus.step4.orm.PhoneOrm;
import de.hse.swa.jaxquarkus.step4.orm.UserOrm;
import javax.ws.rs.QueryParam;



@Path("/users")
public class UserResource {
	
    @ApplicationScoped
    @Inject
    UserOrm userOrm;
   
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public List<User> getUsers() 
   {
       return userOrm.getUsers();
   }
   
    
    @GET
	@Path("{id}")
    //@Path("greeting/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Long id)
    {
        return userOrm.getUser(id); 
    }
  
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean addUser(User usr,@QueryParam("companyId") Long companyId) 
    { 	
    	//if ? addConnection / addUser
        return userOrm.addUser(usr,companyId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(User usr) 
    {
        userOrm.updateUser(usr);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(User usr) 
    {
        userOrm.deleteUser(usr);
    }
    
}