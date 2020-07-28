package jpa_hibernate_spring_boot.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
//@Table(name = "LIST")
public class Parent {

    @Id
    //@Column(name = "LIST_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String job;
    
    @OneToMany(targetEntity = Child.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID", nullable = true)
    private Set<Child> childs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Set<Child> getChilds() {
        if(childs != null) {
            return childs;
        }
        else {
            childs = new HashSet<Child>();
            return childs;
        }
    }

    public void setChilds(Set<Child> childs) {
        this.childs = childs;
    }

}
