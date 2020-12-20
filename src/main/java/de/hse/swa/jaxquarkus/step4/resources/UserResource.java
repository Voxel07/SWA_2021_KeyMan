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
import de.hse.swa.jaxquarkus.step4.orm.UserOrm;

import org.json.JSONObject;


@Path("/users")
public class UserResource {
	
    @ApplicationScoped
    
    @Inject
    UserOrm userOrm;

    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
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
    public void addUser(User usr) 
    { 	
        userOrm.addUser(usr);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(User usr) 
    {
        userOrm.updateUser(usr);
    }
    
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
 //  public void updateUser(JSONObject jo)//Warum geht das nicht  
    public String updateUser(String str) 
    {
//    	System.out.println("der String: "+str);
    	JSONObject jo = new JSONObject(str);
//    	System.out.println("Das kam an:"+jo.toString());
    	Long id = jo.getLong("id");
		String type = jo.getString("type");
		String number = jo.getString("number");
//		System.out.println("Nach der Umwandlung: "+" ID: "+id+" number: "+number+"type: "+type);  	
    	
        return userOrm.addPhone(id,number,type);
    }
    
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(User usr) 
    {
        userOrm.deleteUser(usr);
    }
    
}