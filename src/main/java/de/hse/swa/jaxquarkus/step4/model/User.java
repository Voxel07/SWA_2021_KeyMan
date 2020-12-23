package de.hse.swa.jaxquarkus.step4.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "USER")
public class User {
    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "ZSEQ_USER_ID", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "userSeq")
    
    //@JsonIgnore
    
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "email", unique = true) 
    private String email;

    @Column(name = "username", unique = true)
    private String username;
    
    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "is_admin")
    private boolean isAdmin = false;
    
    //hier falsch ?
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
    		name = "user_contracts",
    		joinColumns = {@JoinColumn(name = "User_id", referencedColumnName="id")},
    		inverseJoinColumns = {@JoinColumn(name = "Contract_id", referencedColumnName="id")}
    		)
    //so ?
//    @ManyToMany(mappedBy="users", fetch = FetchType.LAZY)
    private  List<Contract> contracts = new ArrayList<>();
       
//    @ManyToOne
//    @JoinColumn(name="company_Id")
//    private Company companyU;
    
    //Unidirektional tut
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name="user_id",referencedColumnName = "id")
    
    //Bidirektional geht nicht user id wird nich in Phone eingetragen
   @OneToMany(mappedBy="usr",cascade = {CascadeType.ALL},fetch=FetchType.LAZY )
    
    //zusatz von stack geht aber nicht
   // @JoinColumn(name ="user_id")
    //ende zusatz
	private  List<Phone> phones = new ArrayList<>();


    public User() {
    }

    public User(String email, String username, String password, String firstName, String lastName, boolean isAdmin) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    } 

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
    
    //Ab hier zusammenhang mit der Phone Klasse
    public List<Phone> getPhones(){
    	return this.phones;
    }
    public void setPhones(List<Phone> phones) {
    	this.phones = phones;
    	
    }
    public void removePhone(Phone phone) {
    	getPhones().remove(phone);
    }
    
	public void setContracts(List<Contract> contracts) {
    	this.contracts = contracts;
    }
    public List<Contract> getContracts() {
    	return this.contracts;
    }
        
    @Override
   	public String toString() {
   		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
   				+ ", firstName=" + firstName + ", lastName=" + lastName + ", isAdmin=" + isAdmin + "]";
   	}

}
