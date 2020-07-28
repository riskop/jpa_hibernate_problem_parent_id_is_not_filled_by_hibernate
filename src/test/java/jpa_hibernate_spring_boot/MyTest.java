package jpa_hibernate_spring_boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import jpa_hibernate_spring_boot.entity.Child;
import jpa_hibernate_spring_boot.entity.Parent;
import jpa_hibernate_spring_boot.repository.ParentRepository;

@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "" })
@TestPropertySource("/application.properties")
public class MyTest {

    @Autowired
    private ParentRepository parentRepository;
    
    @Test
    public void test() {
        Child c = new Child();
        c.setHobby("hobby");
        
        Parent p = new Parent();
        p.setJob("test");
        p.getChilds().add(c);
        
        parentRepository.save(p);
    }

}
