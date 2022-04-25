package com.example.student_management.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
//import java.util.List;

@Document(collection = "students")
public class student {
    @Id
    private String id;

    @Field(value = "studentId")
    @NotNull
    @Size(min = 2, message = "studentId should have atleast 2 characters")
    private String studentId;

    @Field(value = "name")
    @NotNull
    @Size(min = 5, message = "studentId should have atleast 5 characters")
    private String name;

    @Field(value="address")
    private String address;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy")
    @Field(value="dateOfBirth")
    private String dateOfBirth;

    @Field(value="facultyId")
    private String facultyId;

//    @DBRef(db= "faculties")
//    private List<faculty> faculties = new ArrayList<>();;

    @CreatedDate
    @Field(value = "createdAt")
    private Date createdAt;

    @UpdateTimestamp
    @LastModifiedDate
    @Field(value = "updatedAt")
    private Date updatedAt;

    public String getId() { return this.id; }

    public String getStudentId() { return this.studentId; }
    public void setStudentId( String studentId ) { this.studentId = studentId; }

    public String getName() { return this.name; }
    public void setName( String name ) { this.name = name; }

    public String getAddress() { return this.address; }
    public void setAddress ( String address ) { this.address = address; }

    public String getFacultyId() { return this.facultyId; }
    public void setFacultyId( String facultyId ) { this.facultyId = facultyId; }

//    public List<faculty> getFaculty() { return this.faculties; }
//    public void setFaculty( List<faculty> faculties ) { this.faculties = faculties; }

    public String getDateOfBirth() { return this.dateOfBirth; }
    public void setDateOfBirth( String dateOfBirth ) { this.dateOfBirth = dateOfBirth; }

    public Date getCreatedAt() { return this.createdAt; }
    public void setCreatedAt( Date createdAt ) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return this.updatedAt; }
    public void setUpdatedAt ( Date updatedAt ) { this.updatedAt = updatedAt; }

    @Override
    public String toString(){
        return "id: "+this.id+
                "studentId: "+ this.studentId +
                ", name: "+ this.name +
                ", address: "+ this.address +
                ", date of birth: "+ this.dateOfBirth +
                ", facultyId: "+ this.facultyId
//               + ", faculty: "+ this.faculties
                ;
    }
}
