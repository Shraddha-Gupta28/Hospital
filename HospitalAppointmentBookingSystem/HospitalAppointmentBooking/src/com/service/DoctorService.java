package com.service;

import com.dao.DoctorDao;
import com.model.Doctor;

import java.sql.SQLException;
import java.util.List;

public class DoctorService {
    private DoctorDao doctorDao;

    // Constructor DoctorDao 
    public DoctorService(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    // Doctor add 
    public void addDoctor(Doctor doctor) throws SQLException {
        // Validation example
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        if (doctor.getName() == null || doctor.getName().isEmpty()) {
            throw new IllegalArgumentException("Doctor name cannot be empty");
        }
        if (doctor.getSpecialization() == null || doctor.getSpecialization().isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }
        doctorDao.addDoctor(doctor);
    }

    // Specialization  doctors list 
    public List<Doctor> getDoctorsBySpecialization(String specialization) throws SQLException {
        if (specialization == null || specialization.isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }
        return doctorDao.getDoctorsBySpecialization(specialization);
    }

    // Id se doctor get karne ki service
    public Doctor getDoctorById(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID");
        }
        return doctorDao.getDoctorById(id);
    }

    // All doctors service
    public List<Doctor> getAllDoctors() throws SQLException {
        return doctorDao.getAllDoctors();
    }

    // Doctor update service
    public void updateDoctor(Doctor doctor) throws SQLException {
        if (doctor == null || doctor.getId() <= 0) {
            throw new IllegalArgumentException("Invalid doctor data");
        }
        doctorDao.updateDoctor(doctor);
    }

    // Doctor delete service
    public void deleteDoctor(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID");
        }
        doctorDao.deleteDoctor(id);
    }

    // Total count doctors  
    public int getDoctorCount() throws SQLException {
        return doctorDao.getDoctorCount();
    }
}
