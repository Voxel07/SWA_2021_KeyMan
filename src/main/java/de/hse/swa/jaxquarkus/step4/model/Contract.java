package de.hse.swa.jaxquarkus.step4.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "CONTRACT")
public class Contract {
    // public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @SequenceGenerator(name = "conSeq", sequenceName = "ZSEQ_con_ID", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "conSeq")
    
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "startDate")
    private String startDate;

    @Column(name = "endDate")
    private String endDate;
    
    @Column(name = "version")
    private String version;
       
    @Column(name = "licenskey", length = 10000)
    private String licenskey;

    //Löscht zu viel
   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "contracts")
    //Löscht nicht genug
   // @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH}, mappedBy = "contracts")
   // private  List<User> users = new ArrayList<>();
    
    //hier richtig ?
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
//    @JoinTable(
//    		name = "user_contracts",
//    		joinColumns = {@JoinColumn(name = "User_id", referencedColumnName="id")},
//    		inverseJoinColumns = {@JoinColumn(name = "Contract_id", referencedColumnName="id")}
//    		)
    private  List<User> users = new ArrayList<>();
    
  //@OneToMany(targetEntity=features.class, mappedBy="crtF", fetch=FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id" ,referencedColumnName = "id")
    private  List<Feature> features = new ArrayList<>(); 
    
//  @OneToMany(targetEntity=ipNumbers.class, mappedBy="crtIP", fetch=FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private  List<IpNumbers> ipNumbers = new ArrayList<>(); 
    
    @ManyToOne
    @JoinColumn(name="company_Id")
    private Company companyC;
    public Contract() {
    }

    public Contract(String startDate, String endDate, String version, String licenskey) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.version = version;
        this.licenskey = licenskey;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    // @JsonIgnore()
    public String getVersion() {
        return version;
    }

    // @JsonProperty()
    public void setVersion(String version) {
        this.version = version;
    }
    public String getLicenskey() {
        return licenskey;
    }
    public void setLicenskey(String licenskey) {
        this.licenskey = licenskey;
    }
    
    //Ab hier Funktionen mit IP & Features & User
    
    public List<User> getUsers(){
    	
    	return this.users;
    }
    
    public void setUsers(List<User> users) {
		this.users = users;
	}
  
    public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> feature) {
		this.features = feature;
	}

	public List<IpNumbers> getIpNumbers() {
		return ipNumbers;
	}

	public void setIpNumbers(List<IpNumbers> ipNumbers) {
		this.ipNumbers = ipNumbers;
	}

	public List <IpNumbers> getIps(){
    	return this.ipNumbers;
    }

	@Override
	public String toString() {
		return "Contract [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", version=" + version
				+ ", licenskey=" + licenskey + "]";
	}
}
