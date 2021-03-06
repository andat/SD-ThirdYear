package com.example.Assignment2_LabApp.service;

import com.example.Assignment2_LabApp.model.entity.Student;

import javax.mail.internet.InternetAddress;
import java.util.List;

public interface IStudentService {
    public List<Student> getAllStudents();
    public Student getStudentById(int id);
    public void addStudent(Student student, String token);
    public void updateStudent(Student student);
    public void deleteStudent(int id);
    public void changePassword(Student student, String password);
}
