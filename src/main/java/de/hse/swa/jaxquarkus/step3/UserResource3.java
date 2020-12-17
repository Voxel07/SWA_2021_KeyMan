package de.hse.swa.jaxquarkus.step3;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import io.vertx.core.http.HttpServerRequest;

@RequestScoped
@Path("/step3/users")
public class UserResource3 {

    @Inject
    UserService service;
    
    @Context
    HttpServerRequest request;

    @GET
    @Produces("application/json")
    public List<User3> greeting() {
    	new User3("username", "password", "fullName", true);
        return service.getUsers();
    }

    @PUT
    @Produces("application/json")  
    @Consumes("application/x-www-form-urlencoded")
    public List<User3> addUser(
    	@FormParam("username") String username,
    	@FormParam("password") String password,
    	@FormParam("fullName") String fullName,
    	@FormParam("isAdmin") Boolean isAdmin) {
    	User3 user = new User3(username, password, fullName, isAdmin);
        return service.addUser(user);
    } 
    
    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public List<User3> updateUser(
        	@FormParam("username") String username,
        	@FormParam("password") String password,
        	@FormParam("fullName") String fullName,
        	@FormParam("isAdmin") Boolean isAdmin,
        	@FormParam("id") Long id) {
    	User3 user = new User3(username, password,fullName, isAdmin);
    	user.setId(id);
        return service.updateUser(user);
    } 

    @DELETE
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public List<User3> removeUser(
        	@FormParam("id") Long id	) {
    	return service.removeUser(id);
    }
   
    
}