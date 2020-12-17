package de.hse.swa.jaxquarkus.step3;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {

	public static List<User3> users = new ArrayList<User3>();
	public static Long id = 1L;
	
    public List<User3> getUsers() {
        return users;
    }
    
    public List<User3> addUser(User3 user) {
    	user.setId(id++);
    	users.add(user);
        return users;
    }
    
    public List<User3> updateUser(User3 user) {
    	for (int index = 0; index < users.size(); ++index) {
    		if (users.get(index).getId().equals(user.getId())) {
    			users.set(index, user);
    			break;
    		}
    	}
        return users;
    }
    
    public List<User3> removeUser(Long id) {
    	for (int index = 0; index < users.size(); ++index) {
    		if (users.get(index).getId().equals(id)) {			
    			users.remove(index);
    			break;
    		}
    	}
        return users;
    }

}