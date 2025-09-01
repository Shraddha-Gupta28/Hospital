package com.controller;

import com.model.Patient;
import com.service.PatientService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class PatientController {

    private PatientService patientService;

    public PatientController() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/hospital", "root", "70240");
            
        patientService = new PatientService(new com.dao.PatientDao(conn));
    }

    public List<Patient> listPatients() throws Exception {
        return patientService.getAllPatients();
    }

    public void addPatient(Patient patient) throws Exception {
        patientService.addPatient(patient);
    }

    public void deletePatient(int id) throws Exception {
        patientService.deletePatient(id);
    }

   
}
