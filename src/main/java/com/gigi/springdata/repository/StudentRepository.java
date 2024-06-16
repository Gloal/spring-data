package com.gigi.springdata.repository;

import com.gigi.springdata.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findTopByOrderByStudentIdDesc();
}
