package com.example.student_management.repositories;

import com.example.student_management.entities.student;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
@EnableMongoAuditing
public interface studentRepository extends MongoRepository<student, String> {
    student findByStudentId(String studentId);
//    List<student> findByName (String name);
    List<student> findByFacultyId (String facultyId);
}
