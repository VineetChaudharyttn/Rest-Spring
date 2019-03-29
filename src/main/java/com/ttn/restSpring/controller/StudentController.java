package com.ttn.restSpring.controller;

import com.ttn.restSpring.customExecption.StudentNotFoundException;
import com.ttn.restSpring.entity.*;
import com.ttn.restSpring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    MessageSource messageSource;
//    Q.1:Create a Rest API for Student using noun plurals for endpoint and http verbs for different operations.
//    Q.3:Print the Validation Messages of student Entity in response
    @PostMapping("/registerStudents")
    ResponseEntity<Student> registerStudents(@Valid @RequestBody Student student) {
        studentService.saveStudent(student);
        URI uri= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}").buildAndExpand(student.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
//  Q.2:Create a Customise Exception Handler.
    @GetMapping("/fatchStudents")
    List<Student> fatchAllStudents(){
        List<Student> student=studentService.allStudent();
        if(student==null){
            throw new StudentNotFoundException("There is no such record to delete");
        }
        return student;
    }

    @GetMapping("/fatchStudent/{id}")
    Student fatchStudent(@PathVariable Integer id){
        return studentService.specificStudent(id).orElse(null);

    }

    @DeleteMapping("/delete/{id}")
    Student deleteById(@PathVariable Integer id){
        Student student=studentService.specificStudent(id).orElse(null);
        if(student==null){
            throw new StudentNotFoundException("There is no such record to delete");
        }
        studentService.deleteStudent(id);
        return student;
    }

//    Perform Internationalization for a greeting message in your app
    @GetMapping("/")
    String helloWorld(@RequestHeader(name = "Accept-Language",required = false) Locale locale){
        return messageSource.getMessage("good.morning.message",null,locale);
    }

    @GetMapping("/e")
    String helloWorld(){
        return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());
    }
//Q.7:Create 2 versions of your API one take reprsent name of the Student as single string and other showing firstname and lastname seperately.

    StudentV1 getPersonV1() {
        return new StudentV1("Vineet Chaudhary");
    }
    @GetMapping("/person/V2")
    StudentV2 getPersonV2() {
        return new StudentV2(new Name("Dhanendra","Kumar"));
    }
    @GetMapping(value = "/person/param",params = "version=1")
    StudentV1 getPersonParam1() {
        return new StudentV1("Vineet Chaudhary");
    }
    @GetMapping(value = "/person/param",params = "version=2")
    StudentV2 getPersonParam2() {
        return new StudentV2(new Name("Dhanendra","Kumar"));
    }
    @GetMapping(value = "/person/header",headers = "API-VERSION=1")
    StudentV1 getPersonHeader1() {
        return new StudentV1("Vineet Chaudhary");
    }
    @GetMapping(value = "/person/header",headers = "API-VERSION=2")
    StudentV2 getPersonHeader2() {
        return new StudentV2(new Name("Dhanendra","Kumar"));
    }

}
