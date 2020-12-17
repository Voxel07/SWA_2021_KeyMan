package de.hse.swa.jaxquarkus.step3;

import javax.ws.rs.FormParam;



public class User3 {

    private Long id;
	
	@FormParam("username")
    private String username;
	
	@FormParam("password") 
    private String password;
	
	@FormParam("firstName")
    private String fullName;
	
	@FormParam("isAdmin")
    private boolean isAdmin = false;

    public User3() {
    }

    public User3(String username, String password, String fullName, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // @JsonIgnore()
    public String getPassword() {
        return password;
    }

    // @JsonProperty()
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

}
