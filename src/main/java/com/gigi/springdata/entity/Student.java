package com.gigi.springdata.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    private Long studentId;
    private String firstName;
    private String lastName;
    private String emailId;
    private LocalDate dob;
    private String guardianName;
    private String guardianEmail;
    private String guardianMobile;

}
