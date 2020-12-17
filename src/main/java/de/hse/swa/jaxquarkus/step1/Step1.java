package de.hse.swa.jaxquarkus.step1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/step1")
public class Step1 {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
    	System.out.println("Step 15:");
        return "hello";
    }
}