package com.example.student_management.controllers;

import com.example.student_management.entities.student;
import com.example.student_management.exceptions.StudentNotFoundException;
import com.example.student_management.repositories.studentRepository;
import com.example.student_management.entities.faculty;
import com.example.student_management.repositories.facultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
public class studentController {

    @Autowired
    private Environment env;

    @Autowired
    private studentRepository studentRepository;
    @Autowired
    private facultyRepository facultyRepository;

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("API test working . . . " + env.getProperty("MONGODB_COLLECTION_NAME"));
    }

    //get - api/v1/students
    @GetMapping("/students")
    public List<student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    //get - api/v1/students/:studentId
    @GetMapping("/students/{studentId}")
    public ResponseEntity getStudentById(@PathVariable String studentId) {
        student stu = studentRepository.findByStudentId(studentId);
        if (stu != null)
            return ResponseEntity.status(HttpStatus.OK).body(stu);
//        return ResponseEntity.status(HttpStatus.OK).body(stu.getId());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found student");
        throw new StudentNotFoundException("id - " + studentId+" not found!");
        /// . . . throw the exception Not found
    }

    //post - api/v1/students
    @PostMapping("/students")
    public ResponseEntity createStudent(@Valid @RequestBody student newStudent) throws ParseException {
        if (newStudent == null || newStudent.getStudentId() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing the student . . .");
        // . . . throw the exception no content
        //check studentId is exists
        if (studentRepository.findByStudentId(newStudent.getStudentId()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student is existing . . .");
        // . . . throw the exception isExisting
        if(facultyRepository.findByFacultyId(newStudent.getFacultyId())==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("facultyId is wrong . . . ");
        newStudent.setStudentId(newStudent.getStudentId().toLowerCase());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        newStudent.setCreatedAt(formatter.parse(formatter.format(date)));
        newStudent.setUpdatedAt(formatter.parse(formatter.format(date)));
        studentRepository.save(newStudent);
//        String facId = facultyRepository.findByFacultyId(newStudent.getFacultyId()).getFacultyId();
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    //put - api/v1/students/:studentId
    @PutMapping("/students/{studentId}")
    public ResponseEntity updateStudent(@RequestBody student updatedStudent, @PathVariable String studentId) throws ParseException {
        if (updatedStudent == null || updatedStudent.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing the student . . .");
            /// . . . throw the exception no content
        }
        student stu = studentRepository.findByStudentId(studentId);
        if (stu != null) {
            stu.setName(updatedStudent.getName());
            stu.setAddress(updatedStudent.getAddress());
            stu.setFacultyId(updatedStudent.getFacultyId());
            stu.setDateOfBirth(updatedStudent.getDateOfBirth());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            stu.setUpdatedAt(formatter.parse(formatter.format(date)));
            if(facultyRepository.findByFacultyId(stu.getFacultyId())==null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("facultyId is wrong . . . ");
            studentRepository.save(stu);
            return ResponseEntity.status(HttpStatus.OK).body(stu);
        }
        //student not found
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("student " + studentId + " not found . . .");
        throw new StudentNotFoundException("id - " + studentId+" not found!");
        // . . . throw the exception Not found
    }

    //delete - api/v1/students/:studentId
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable String studentId) {
        //get student need delete
        student stu = studentRepository.findByStudentId(studentId);
        if (stu != null) {
            studentRepository.deleteById(stu.getId());
            return ResponseEntity.status(HttpStatus.OK).body(stu);
        }
        //student not found
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("student " + studentId + " not found . . .");
        throw new StudentNotFoundException("id - " + studentId+" not found!");
        // . . . throw the exception Not found
    }

}
