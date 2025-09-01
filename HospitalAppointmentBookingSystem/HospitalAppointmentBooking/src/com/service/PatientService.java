package com.service;

import com.dao.PatientDao;
import com.model.Patient;

import java.sql.SQLException;
import java.util.List;

public class PatientService {
    private PatientDao patientDao;

    // Constructor  
    public PatientService(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    // Patient add 
    public void addPatient(Patient patient) throws SQLException {
    	
        //  additional validation 
        if(patient.getName() == null || patient.getName().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be empty");
        }
        if(patient.getAge() < 0) {
            throw new IllegalArgumentException("Patient age cannot be negative");
        }
        patientDao.addPatient(patient);
    }

    // Id patient 
    public Patient getPatientById(int id) throws SQLException {
        return patientDao.getPatientById(id);
    }

    // Age range patients get 
    public List<Patient> getPatientsByAgeRange(int minAge, int maxAge) throws SQLException {
        if(minAge > maxAge) {
            throw new IllegalArgumentException("minAge cannot be greater than maxAge");
        }
        return patientDao.getPatientsByAgeRange(minAge, maxAge);
    }

    // Gender patients get 
    public List<Patient> getPatientsByGender(String gender) throws SQLException {
        if(gender == null || gender.isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be empty");
        }
        return patientDao.getPatientsByGender(gender);
    }

    //patients get 
    public List<Patient> getAllPatients() throws SQLException {
        return patientDao.getAllPatients();
    }

    // Update patient 
    public void updatePatient(Patient patient) throws SQLException {
        if(patient.getId() <= 0) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        patientDao.updatePatient(patient);
    }

    // Patient delete
    public void deletePatient(int id) throws SQLException {
        if(id <= 0) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        patientDao.deletePatient(id);
    }
}
