package jpa_hibernate_spring_boot.repository;

import org.springframework.stereotype.Repository;

import jpa_hibernate_spring_boot.entity.Parent;

import org.springframework.data.jpa.repository.JpaRepository;;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

}
