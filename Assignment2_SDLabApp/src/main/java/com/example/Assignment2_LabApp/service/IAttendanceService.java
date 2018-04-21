package com.example.Assignment2_LabApp.service;

import com.example.Assignment2_LabApp.model.Attendance;

import java.util.List;

public interface IAttendanceService {
    public List<Attendance> getAllAttendances();
    public Attendance getAttendanceById(int id);
    public void addAttendance(Attendance attendance);
    public void updateAttendance(Attendance attendance);
    public void deleteAttendance(int id);
}
