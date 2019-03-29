package com.ttn.restSpring.repository;

import com.ttn.restSpring.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends CrudRepository<Student, Integer> {
    List<Student> findAll();
}
