package com.service;

import com.dao.AppointmentDao;
import com.model.Appointment;
import com.model.Doctor;
import com.model.Patient;

import java.sql.SQLException;
import java.util.List;

public class AppointmentService {
    private AppointmentDao appointmentDao;

    // Constructor 
    public AppointmentService(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    // book a new appointment
    public void bookAppointment(Appointment appointment) throws SQLException {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        if (appointment.getPatientId() <= 0) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        if (appointment.getDoctorId() <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID");
        }
        if (appointment.getDate() == null) {
            throw new IllegalArgumentException("Appointment date cannot be null");
        }
        if (appointment.getTime() == null) {
            throw new IllegalArgumentException("Appointment time cannot be null");
        }
        appointmentDao.bookAppointment(appointment);
    }

    // get all doctors
    public List<Doctor> getAllDoctors() throws SQLException {
        return appointmentDao.getAllDoctors();
    }

    // register a new patient
    public void registerPatient(Patient patient) throws SQLException {
        if (patient == null) {
            throw new IllegalArgumentException("Patient object cannot be null");
        }
        if (patient.getName() == null || patient.getName().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be empty");
        }
        if (patient.getAge() < 0) {
            throw new IllegalArgumentException("Patient age cannot be negative");
        }
        if (patient.getGender() == null || patient.getGender().isEmpty()) {
            throw new IllegalArgumentException("Patient gender cannot be empty");
        }
        appointmentDao.registerPatient(patient);
    }

    // get all appointment history
    public List<Appointment> getAppointmentHistory() throws SQLException {
        return appointmentDao.getAppointmentHistory();
    }

    // fixed viewHistory 
    public List<Appointment> viewHistory() throws SQLException {
        return appointmentDao.getAppointmentHistory();
    }
    
    public void deleteAppointment(int id) throws SQLException {
    	appointmentDao.deleteAppointment(id);
    }
}

