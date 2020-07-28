package jpa_hibernate_spring_boot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Table(name = "ELEMENT")
public class Child {

    @Id
    //@Column(name = "LIST_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    //@Column(name = "NAME", length = 255, nullable = false)
    private String hobby;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

}
