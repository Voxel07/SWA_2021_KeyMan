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
@Table(name ="Feature")
public class Feature{

    @Id
    @SequenceGenerator(name = "feaSeq", sequenceName = "ZSEQ_fea_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "feaSeq")
    
    @Column(name = "id", unique = true)
    private Long id;
    
    // @Column(name = "number", unique = true)
    @Column(name = "number")
    private String number;
        
    @ManyToOne
    @JoinColumn(name ="contract_id", referencedColumnName="id")
    private Contract crtF;
    
    
    public Feature() {
    	
    }
    public Feature(String number) {
    	this.number = number;
    }
    
    public void setId(Long id) {
        this.id = id;
    } 
    public Long getId() {
        return id;
    }
    
    public String getNumber() {
        return number;
    }

    public void setNumber(String name) {
        this.number = name;
    }
    @JsonbTransient
    public Contract getCrtF() {
		return crtF;
	}
	public void setCrtF(Contract crtF) {
		this.crtF = crtF;
	}

	@Override
	public String toString() {
		return "Feature [id=" + id + ", number=" + number + "]";
	}
    
}