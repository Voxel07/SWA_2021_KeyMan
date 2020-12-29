package de.hse.swa.jaxquarkus.step4.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;

@Entity
@Table(name ="ipNumber")
public class IpNumber{

    @Id
    @SequenceGenerator(name = "ipSeq", sequenceName = "ZSEQ_ip_ID", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "ipSeq")
    
    @Column(name = "id", unique = true)
    private Long id;
    
    @Column(name="number", unique = true)
    private String ipNumber;
    
    @ManyToOne
    @JoinColumn(name ="contract_id", referencedColumnName="id")
    private Contract crtIP;
    
    
  
    public IpNumber() {
    	
    }
    public IpNumber(String number) {
    	this.ipNumber = number;
    }
    
    public void setId(Long id) {
        this.id = id;
    } 
    public Long getId() {
        return id;
    }
    
    public String getIpNumber() {
		return ipNumber;
	}
    
	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
    }
    
    @JsonbTransient
	public Contract getCrtIP() {
		return crtIP;
	}
	
	public void setCrtIP(Contract crtIP) {
		this.crtIP = crtIP;
    }
    
	@Override
	public String toString() {
		return "IpNumbers [id=" + id + ", ipNumber=" + ipNumber + "]";
	}
    
}