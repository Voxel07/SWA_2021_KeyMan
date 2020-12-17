package de.hse.swa.jaxquarkus.step4.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name ="Phone")
public class Phone{

    @Id
    @SequenceGenerator(name = "phoneSeq", sequenceName = "ZSEQ_phone_ID", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "phoneSeq")
    
    @Column(name = "id", unique = true)
    private Long id;
    
    @Column(name = "number", unique = true)
    private String number;
    
    @Column(name = "type")
    private String type;
    //Uni ohne irgendwas
    
    //Bi    
//      @ManyToOne(cascade = CascadeType.ALL)
//      @JoinColumn(name ="user_id")
//      private User usr;
    
      
    public Phone() {
    	
    }
    
    public Phone(String number, String type) {
    	this.number = number;
    	this.type = type;
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
    
    public String getType() {
        return type;
    }

    public void setType (String name) {
        this.type = name;
    }
//    public User getUser() {
//    	return usr;
//    }
//   
//    public void setUser(User usr) {
//    	this.usr = usr;
//    	
//    }

	@Override
	public String toString() {
		return "Phone [id=" + id + ", number=" + number + ", type=" + type + "]";
	}
    

   
   
}