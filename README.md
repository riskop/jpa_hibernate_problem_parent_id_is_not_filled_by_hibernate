# jpa_hibernate_problem_parent_id_is_not_filled_by_hibernate

I have a unidirectional one to many relationship. The one side is PARENT, the many side is CHILD. For one PARENT there can be many CHILD. But for a CHILD there is exactly one PARENT. On the Java side the relation is unidirectional, I need to access the CHILDS of a PARENT, but I don't want to store the PARENT for CHILDS. So these are the objects:

Parent:

    @Entity
    public class Parent {
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        
        private String job;
        
        @OneToMany(targetEntity = Child.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinColumn(name = "PARENT_ID", nullable = true)
        private Set<Child> childs;
    
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getJob() { return job; }
        public void setJob(String job) { this.job = job; }
    
        public Set<Child> getChilds() {
            if(childs != null) { return childs; }
            else {
                childs = new HashSet<Child>();
                return childs;
            }
        }
        public void setChilds(Set<Child> childs) { this.childs = childs; }
    
    }

Child:

    @Entity
    public class Child {
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String hobby;
    
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getHobby() { return hobby; }
        public void setHobby(String hobby) { this.hobby = hobby; }
    
    }

This is the code which creates a child, a parent with that child and then saves parent:

    @Test
    public void test() {
        Child c = new Child();
        c.setHobby("hobby");
        
        Parent p = new Parent();
        p.setJob("test");
        p.getChilds().add(c);
        
        parentRepository.save(p);
    }


Then when I run the code there is an error because Hibernate does not set the PARENT_ID on CHILD when inserting it. In the log it is clear that Hibernate retrieved the two ids needed from the sequence generator YET it leaves CHILD.PARENT_ID null:

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

    2020-07-28 13:21:00.689  INFO 16295 --- [           main] jpa_hibernate_spring_boot.MyTest         : Starting MyTest on riskop-ESPRIMO-P556 with PID 16295 (started by riskop in /home/riskop/Documents/privat/java/jpa_hibernate_spring_boot)
    2020-07-28 13:21:00.690  INFO 16295 --- [           main] jpa_hibernate_spring_boot.MyTest         : No active profile set, falling back to default profiles: default
    2020-07-28 13:21:00.950  INFO 16295 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFERRED mode.
    2020-07-28 13:21:00.988  INFO 16295 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 32ms. Found 1 JPA repository interfaces.
    2020-07-28 13:21:01.362  INFO 16295 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
    2020-07-28 13:21:01.491  INFO 16295 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
    2020-07-28 13:21:01.608  INFO 16295 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
    2020-07-28 13:21:01.660  INFO 16295 --- [         task-1] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
    2020-07-28 13:21:01.703  INFO 16295 --- [         task-1] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.4.18.Final
    2020-07-28 13:21:01.743  INFO 16295 --- [           main] DeferredRepositoryInitializationListener : Triggering deferred initialization of Spring Data repositories…
    2020-07-28 13:21:01.820  INFO 16295 --- [         task-1] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.0.Final}
    2020-07-28 13:21:01.977  INFO 16295 --- [         task-1] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
    2020-07-28 13:21:02.388  INFO 16295 --- [         task-1] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
    2020-07-28 13:21:02.393  INFO 16295 --- [         task-1] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
    2020-07-28 13:21:02.521  INFO 16295 --- [           main] DeferredRepositoryInitializationListener : Spring Data repositories initialized!
    2020-07-28 13:21:02.526  INFO 16295 --- [           main] jpa_hibernate_spring_boot.MyTest         : Started MyTest in 1.983 seconds (JVM running for 2.575)
    2020-07-28 13:21:02.578 DEBUG 16295 --- [           main] org.hibernate.SQL                        : 
        call next value for hibernate_sequence
    2020-07-28 13:21:02.595 DEBUG 16295 --- [           main] org.hibernate.SQL                        : 
        call next value for hibernate_sequence
    2020-07-28 13:21:02.601 DEBUG 16295 --- [           main] org.hibernate.SQL                        : 
        insert 
        into
            parent
            (job, id) 
        values
            (?, ?)
    2020-07-28 13:21:02.603 TRACE 16295 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [test]
    2020-07-28 13:21:02.604 TRACE 16295 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [BIGINT] - [1]
    2020-07-28 13:21:02.605 DEBUG 16295 --- [           main] org.hibernate.SQL                        : 
        insert 
        into
            child
            (hobby, id) 
        values
            (?, ?)
    2020-07-28 13:21:02.606 TRACE 16295 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [hobby]
    2020-07-28 13:21:02.606 TRACE 16295 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [BIGINT] - [2]
    2020-07-28 13:21:02.607  WARN 16295 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 23502, SQLState: 23502
    2020-07-28 13:21:02.607 ERROR 16295 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : NULL not allowed for column "PARENT_ID"; SQL statement:
    insert into child (hobby, id) values (?, ?) [23502-200]
    [ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 2.105 s <<< FAILURE! - in jpa_hibernate_spring_boot.MyTest
    [ERROR] test  Time elapsed: 0.089 s  <<< ERROR!
    org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement
            at jpa_hibernate_spring_boot.MyTest.test(MyTest.java:33)
    Caused by: org.hibernate.exception.ConstraintViolationException: could not execute statement
            at jpa_hibernate_spring_boot.MyTest.test(MyTest.java:33)
    Caused by: org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: 
    NULL not allowed for column "PARENT_ID"; SQL statement:
    insert into child (hobby, id) values (?, ?) [23502-200]
            at jpa_hibernate_spring_boot.MyTest.test(MyTest.java:33)
    


How should I fix it?

Note that if I remove the not null constraint from CHILD.PARENT_ID, then the code works. But I obviously need that check.

