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

import de.hse.swa.jaxquarkus.step4.model.*;
import de.hse.swa.jaxquarkus.step4.orm.*;
import javax.ws.rs.QueryParam;



@Path("/user")
public class UserResource {
	
    @ApplicationScoped
    @Inject
    UserOrm userOrm;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<User> getUser(	@QueryParam("usrId") Long usrId,
    							@QueryParam("companyId") Long companyId,
                                @QueryParam("username") String usrname,
                                @QueryParam("ctrIdU") Long ctrIdU)
    {   
    	System.out.println("UserResource/getUser");
        if(usrId != null){
        	System.out.println("getUserById");
          return userOrm.getUserById(usrId);
        } 
        else if(companyId!=null){
            System.out.println("getUserByCompany");
           List<User> fu = userOrm.getUserByCompany(companyId);
        //    fu.add(userOrm.getUserByCompany(companyId));
           System.out.println("res"+ fu);
            return fu;
        }
        else if(usrname!=null){
        	System.out.println("getUserByUsername");
            return userOrm.getUserByUsername(usrname);
        }
        else if(ctrIdU !=null){
            System.out.println("getUserByUsername");
            return userOrm.getUserByContract(ctrIdU);
        }
        else{  
        	System.out.println("getUsers");
            return  userOrm.getUsers();
        }
    }

    @PUT
    @Path("{companyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addUser(User usr,@PathParam("companyId") Long companyId) 
    { 	
    	System.out.println("UserResource/addUser");
        return userOrm.addUser(usr,companyId);
    }
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String loginUser(User usr) 
    {
        System.out.println("UserResource/loginUser");
        return userOrm.loginUser(usr);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(User usr) 
    {
    	System.out.println("UserResource/updateUser");
        System.out.println(usr);
        userOrm.updateUser(usr);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(User usr) 
    {
    	System.out.println("UserResource/deleteUser");

        userOrm.deleteUser(usr);
    }
    
}