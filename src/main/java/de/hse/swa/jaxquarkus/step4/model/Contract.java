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
    @SequenceGenerator(name = "conSeq", sequenceName = "ZSEQ_con_ID", allocationSize = 1, initialValue = 1)
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

   @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "contracts")
    private  List<User> users = new ArrayList<>();
   
    @OneToMany(mappedBy="crtF",cascade = {CascadeType.ALL}, fetch=FetchType.LAZY)
    private  List<Feature> features = new ArrayList<>(); 
    
    @OneToMany(mappedBy="crtIP",cascade = {CascadeType.ALL}, fetch=FetchType.LAZY)
    private  List<IpNumber> ipNumbers = new ArrayList<>(); 
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="company_Id", referencedColumnName="id")
    private Company companyC;

    public Contract() {
    }

    public Contract(String startDate, String endDate, String version) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.version = version;
    }

    public Long getId() {
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

    public String getVersion() {
        return version;
    }

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
    @JsonbTransient
    public List<User> getUsers(){
    	return this.users;
    }
    public void setUsers(List<User> users) {
		this.users = users;
	}
    @JsonbTransient
    public Company getCompanyC() {
		return companyC;
	}

	public void setCompanyC(Company companyC) {
		this.companyC = companyC;
	}

    @JsonbTransient
    public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> feature) {
		this.features = feature;
	}
    @JsonbTransient
	public List<IpNumber> getIpNumbers() {
		return ipNumbers;
	}

	public void setIpNumbers(List<IpNumber> ipNumbers) {
		this.ipNumbers = ipNumbers;
	}

	@Override
	public String toString() {
		return "Contract [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", version=" + version
				+ ", licenskey=" + licenskey + "]";
	}
}
