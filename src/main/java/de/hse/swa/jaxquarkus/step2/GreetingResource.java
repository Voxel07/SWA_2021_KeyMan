package de.hse.swa.jaxquarkus.step2;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import io.vertx.core.http.HttpServerRequest;

@RequestScoped
@Path("/step2")
public class GreetingResource {

    @Inject
    GreetingService service;
    
    @Context
    HttpServerRequest request;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("greeting/{name}")
    public String greeting(@PathParam("name") String name, @Context HttpServerRequest request) {
        return service.greeting(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello plain";
    }
}