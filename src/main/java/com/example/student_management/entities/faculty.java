package com.example.student_management.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Document(collection = "faculties")
public class faculty {

    @Id
    private String id;

    @Field(value = "facultyId")
    @NotNull
    @Size(min = 2, message = "facultyId should have atleast 2 characters")
    private String facultyId;

    @Field(value = "name")
    @NotNull
    @Size(min = 5, message = "name should have atleast 5 characters")
    private String name;

    @Field(value = "createdAt")
    @CreationTimestamp
    private Date createdAt;

    @Field(value = "updatedAt")
    @UpdateTimestamp
    private Date updatedAt;

    public String getId() { return this.id; }

    public String getFacultyId() { return this.facultyId; }
    public void setFacultyId( String facultyId ) { this.facultyId = facultyId; }

    public String getName() { return this.name; }
    public void setName( String name ) { this.name = name; }

    public Date getCreatedAt() { return this.createdAt; }
    public void setCreatedAt( Date createdAt ) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return this.updatedAt; }
    public void setUpdatedAt ( Date updatedAt ) { this.updatedAt = updatedAt; }

    @Override
    public String toString(){
        return "id: "+this.id+
                "facultyId: "+ this.facultyId +
                ", name: "+ this.name;
    }
}
