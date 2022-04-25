package com.example.student_management.repositories;

import com.example.student_management.entities.faculty;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface facultyRepository extends MongoRepository<faculty, String> {
    faculty findByFacultyId(String facultyId);
//    faculty findByNameOne(String name);
}
