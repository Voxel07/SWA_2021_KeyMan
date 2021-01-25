package de.hse.swa.jaxquarkus.step4.model;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "USER")
public class User {
    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "ZSEQ_USER_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "userSeq")
    
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "email", unique = true) 
    private String email;

    @Column(name = "username", unique = true)
    private String username;
    
    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;
    
    @Column(name = "lastName")
    private String lastName;
    
    @Column(name = "isAdmin")
    private boolean isAdmin = false;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
    		name = "Rolf",
    		joinColumns = {@JoinColumn(name = "UserId", referencedColumnName="id")},
    		inverseJoinColumns = {@JoinColumn(name = "ContractId", referencedColumnName="id")}
    		)

    private  List<Contract> contracts = new ArrayList<>();
       
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="companyId", referencedColumnName="id")
    private Company companyU;
    
    @OneToMany(mappedBy="usr",cascade = {CascadeType.ALL},fetch=FetchType.LAZY )
	private List<Phone> phones = new ArrayList<>();


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
    @JsonbTransient
    public List<Phone> getPhones(){
    	return this.phones;
    }
    public void setPhones(List<Phone> phones) {
    	this.phones = phones;
    	
    }
    public void removePhone(Phone phone) {
    	getPhones().remove(phone);
    }

    //Ab hier zusammenhang mit der Contract Klasse
	public void setContracts(List<Contract> contracts) {
    	this.contracts = contracts;
    }
    @JsonbTransient
    public List<Contract> getContracts() {
    	return this.contracts;
    }
   
    @JsonbTransient
    public Company getCompanyU() {
		return companyU;
	}

	public void setCompanyU(Company companyU) {
		this.companyU = companyU;
	}

	@Override
   	public String toString() {
   		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
   				+ ", firstName=" + firstName + ", lastName=" + lastName + ", isAdmin=" + isAdmin + "]";
   	}

}
