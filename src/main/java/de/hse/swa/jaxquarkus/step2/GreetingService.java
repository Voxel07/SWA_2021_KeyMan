package de.hse.swa.jaxquarkus.step2;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    public String greeting(String name) {
    	System.out.println("Hello " + name + "!!!");
        return "hello " + name;
    }

}