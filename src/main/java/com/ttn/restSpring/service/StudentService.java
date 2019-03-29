package com.ttn.restSpring.service;

import com.ttn.restSpring.entity.Student;
import com.ttn.restSpring.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepo studentRepo;

    public void saveStudent(Student student){
        studentRepo.save(student);
    }

    public List<Student> allStudent() {
        return (List<Student>) studentRepo.findAll();
    }

    public Optional<Student> specificStudent(Integer id) {
        return studentRepo.findById(id);
    }
    public void deleteStudent(Integer id){
        studentRepo.deleteById(id);
    }
}
