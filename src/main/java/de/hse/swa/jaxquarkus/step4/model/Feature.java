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
@Table(name ="Features")
public class Feature{

    @Id
    @SequenceGenerator(name = "feaSeq", sequenceName = "ZSEQ_fea_ID", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "feaSeq")
    
    @Column(name = "id", unique = true)
    private Long id;
    
    @Column(name = "number", unique = true)
    private String number;
        
//    @ManyToOne
//    @JoinColumn(name ="contract_id")
//    private Contract crtF;
    
    
    public Feature() {
    	
    }
    public Feature(String number) {
    	this.number = number;
    }
    
    public void setId(Long id) {
        this.id = id;
    } 
    public long getId() {
        return id;
    }
    
    public String getNumber() {
        return number;
    }

    public void setNumber(String name) {
        this.number = name;
    }
    
//    public Contract getContract() {
//    	return this.crtF;
//    }
//    
//    public void setContract(Contract crt) {
//   	this.crtF = crt;
//    }
	@Override
	public String toString() {
		return "Feature [id=" + id + ", number=" + number + "]";
	}
    
}