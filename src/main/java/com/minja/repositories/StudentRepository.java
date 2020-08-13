package com.minja.repositories;

import java.util.Collection;

import com.minja.entities.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	public Collection<Student> findByImeStartingWith(String ime);

}
