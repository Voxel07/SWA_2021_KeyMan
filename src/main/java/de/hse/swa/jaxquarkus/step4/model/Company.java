package de.hse.swa.jaxquarkus.step4.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "COMPANY")
public class Company {
    // public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @SequenceGenerator(name = "cmpySeq", sequenceName = "ZSEQ_cmpy_ID", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "cmpySeq")
    
    @Column(name = "id", unique = true)
    private Long id;
   
    @Column(name = "name")
  //  @Column(name = "name", unique = true)
    private String name;

    @Column(name = "department")
    private String department;
    
    @Column(name = "street")
    private String street;
    
    @Column(name = "postalcode", length = 5)
    private int postalcode;

    @Column(name = "state")
    private String state;
    
    @Column(name = "country")
    private String country;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private  List<User> users = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
	private  List<Contract> contracts = new ArrayList<>();
    

    public Company() {
    }

    public Company(String name, String department, String street, int postalcode, String state, String country) {
        this.name = name;
        this.department = department;
        this.street = street;
        this.postalcode = postalcode;
        this.state = state;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }
    
    public void getPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }

    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
   
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public int getPostalcode() {
		return postalcode;
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", department=" + department + ", street=" + street
				+ ", postalcode=" + postalcode + ", state=" + state + ", country=" + country + "]";
	}
    

}
