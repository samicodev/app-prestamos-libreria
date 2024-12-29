package com.samicodev.service;

import com.samicodev.model.Student;
import java.util.List;
import java.util.Optional;

public interface IStudentService {
    void registerStudent(Student student) ;
    List<Student> listStudents();
    Optional<Student> findStudentByDni(String dni);
}
