package de.hse.swa.jaxquarkus.step4.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name ="ipNumbers")
public class IpNumbers{

    @Id
    @SequenceGenerator(name = "ipSeq", sequenceName = "ZSEQ_ip_ID", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "ipSeq")
    
    @Column(name = "id", unique = true)
    private Long id;
    
    @Column(name="number")
    private String ipNumber;
    
//    @ManyToOne
//    @JoinColumn(name ="contract_id")
//    private Contract crtIP;
    
    
  
    public IpNumbers() {
    	
    }
    public IpNumbers(String number) {
    	this.ipNumber = number;
    }
    
    public void setId(Long id) {
        this.id = id;
    } 
    public long getId() {
        return id;
    }
    
    public String getNumber() {
        return this.ipNumber;
    }

    public void setNumber(String number) {
        this.ipNumber = number;
    }
    
//    public Contract getContract() {
//    	return this.crtIP;
//    }
//    
//    public void setContract(Contract crt) {
//   	this.crtIP = crt;
//    }
	@Override
	public String toString() {
		return "IpNumbers [id=" + id + ", ipNumber=" + ipNumber + "]";
	}
    
}