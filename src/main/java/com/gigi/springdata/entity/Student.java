package com.gigi.springdata.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        //name= "tbl_student"    for when the table name isn't the Entity class name
        uniqueConstraints = @UniqueConstraint(
                name = "emailid_unique",
                columnNames = "email_address"
        )
        )
public class Student {

    @Id
    @Column(name="student_id")
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(
            name = "custom-id-generator",
            type = com.gigi.springdata.id.CustomIdGenerator.class,
            parameters = { @org.hibernate.annotations.Parameter(name = "prefix", value = "ST-"),
                            @org.hibernate.annotations.Parameter(name = "idColumnName", value = "student_id"),
                                })
    private String studentId;
    private String firstName;
    private String lastName;

    @Column(
            name = "email_address",
            nullable = false)
    private String emailId;
    private LocalDate dob;
    private String guardianName;
    private String guardianEmail;
    private String guardianMobile;

}
