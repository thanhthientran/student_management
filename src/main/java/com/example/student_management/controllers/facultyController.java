package com.example.student_management.controllers;

import com.example.student_management.entities.faculty;
import com.example.student_management.entities.student;
import com.example.student_management.exceptions.falcutyException.FacultyExistingException;
import com.example.student_management.exceptions.falcutyException.FacultyNoContentException;
import com.example.student_management.exceptions.falcutyException.FacultyNotFoundException;
import com.example.student_management.repositories.facultyRepository;
import com.example.student_management.repositories.studentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class facultyController {

    @Autowired
    private facultyRepository facultyRepository;
    @Autowired
    private studentRepository studentRepository;

    @GetMapping("/faculties")
    public List<faculty> getAllFaculties(){
        return this.facultyRepository.findAll();
    }

    @GetMapping("/faculties/{facultyId}")
    public ResponseEntity getFacultyByFacultyId(@PathVariable String facultyId){
        faculty fac = facultyRepository.findByFacultyId(facultyId);
        if(fac != null)
            return ResponseEntity.status(HttpStatus.OK).body(fac);
        //faculty not found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("faculty " + facultyId + " not found . . .");
        // . . . throw the exception Not found
    }

    @GetMapping("/faculties/{facultyId}/students")
    public ResponseEntity listStudentsInFaculty(@PathVariable String facultyId){
        //check Faculty is existing
        if(facultyRepository.findByFacultyId(facultyId)==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("facultyId is wrong . . . ");
        List<student> lst = studentRepository.findByFacultyId(facultyId);
        //check students in this faculty
        if(lst.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not_Found");
            //throw students not found
        }
        List<String> lstStr = new ArrayList<String>();
        //get list student _id
        for(student stu : lst){
            lstStr.add(stu.getId());
        }
        return ResponseEntity.status(HttpStatus.OK).body(lst);
    }

    //post - api/v1/faculties
    @PostMapping("/faculties")
    public ResponseEntity createFaculty(@Valid @RequestBody faculty newFaculty) throws ParseException {
        if (newFaculty.getName() == null)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing the faculty . . .");
            throw new FacultyNoContentException("faculty is missing . . .");
        //check studentId is exists
        if (facultyRepository.findByFacultyId(newFaculty.getFacultyId()) != null)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("faculty is existing . . .");
            throw new FacultyExistingException("faculty is existing . . .");
        newFaculty.setFacultyId(newFaculty.getFacultyId().toLowerCase());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        newFaculty.setCreatedAt(formatter.parse(formatter.format(date)));
        newFaculty.setUpdatedAt(formatter.parse(formatter.format(date)));
        facultyRepository.save(newFaculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFaculty);
    }

    //put - api/v1/faculties/:facultyId
    @PutMapping("/faculties/{facultyId}")
    public ResponseEntity updateFaculty ( @Valid @RequestBody faculty updatedFaculty, @PathVariable String facultyId ) throws ParseException {
        if (updatedFaculty.getName() == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing the Faculty . . .");
            throw new FacultyNoContentException("faculty is missing . . .");
        }
        faculty fac = facultyRepository.findByFacultyId(facultyId);
        if (fac != null) {
            fac.setName(updatedFaculty.getName());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            fac.setUpdatedAt(formatter.parse(formatter.format(date)));
            facultyRepository.save(fac);
            return ResponseEntity.status(HttpStatus.OK).body(fac);
        }
        //student not found
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("faculty " + facultyId + " not found . . .");
        throw new FacultyNotFoundException("faculty " + facultyId+" not found!");
    }

    @DeleteMapping("/faculties/{facultyId}")
    public ResponseEntity deleteFaculty(@PathVariable String facultyId) {
        //get faculty need delete
        faculty fac = facultyRepository.findByFacultyId(facultyId);
        if(fac != null) {
            //check is Student in this faculty
            List<student> lst = studentRepository.findByFacultyId(facultyId);
            if(lst.isEmpty()){  //is not student in this faculty
                facultyRepository.deleteById(fac.getId());
                return ResponseEntity.status(HttpStatus.OK).body(fac);
            }
            else{
                //get list _id's students
                List<String> lstId = new ArrayList<String>();
                //get list student _id
                for(student stu : lst){
                    stu.setFacultyId(""); //set facultyId of student -> null
                    studentRepository.save(stu);
                }
                facultyRepository.deleteById(fac.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body(fac);
        }
        //faculty not found
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("faculty " + facultyId + " not found . . .");
        throw new FacultyNotFoundException("faculty " + facultyId+" not found!");
    }
}
