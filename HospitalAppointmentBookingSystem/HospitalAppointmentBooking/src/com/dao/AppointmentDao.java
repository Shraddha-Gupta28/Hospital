package com.dao;

import com.model.Appointment;
import com.model.Doctor;
import com.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {

    private Connection conn;

    // Constructor with DB connection
    public AppointmentDao(Connection conn) {
        this.conn = conn;
    }

    // Book a new appointment
    public void bookAppointment(Appointment appt) throws SQLException {
        String sql = "INSERT INTO Appointment (patient_id, doctor_id, appointment_date, appointment_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appt.getPatientId());
            ps.setInt(2, appt.getDoctorId());
            ps.setDate(3, appt.getDate());  // java.sql.Date
            ps.setTime(4, appt.getTime());  // java.sql.Time
            ps.executeUpdate();
        }
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctor";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                doctors.add(new Doctor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization")
                ));
            }
        }
        return doctors;
    }

    // Register a new patient
    public void registerPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO Patient (name, age, gender) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getName());
            ps.setInt(2, patient.getAge());
            ps.setString(3, patient.getGender());
            ps.executeUpdate();
        }
    }

    // Get all appointments
    public List<Appointment> getAppointmentHistory() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                appointments.add(new Appointment(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getDate("appointment_date"),
                    rs.getTime("appointment_time")
                ));
            }
        }
        return appointments;
    }

    // Get appointments by patient_id
    public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment WHERE patient_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(new Appointment(
                        rs.getInt("id"),
                        patientId,
                        rs.getInt("doctor_id"),
                        rs.getDate("appointment_date"),
                        rs.getTime("appointment_time")
                    ));
                }
            }
        }
        return appointments;
    }

    // Get appointments by doctor_id
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment WHERE doctor_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(new Appointment(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        doctorId,
                        rs.getDate("appointment_date"),
                        rs.getTime("appointment_time")
                    ));
                }
            }
        }
        return appointments;
    }

    // Update appointment
    public void updateAppointment(Appointment appt) throws SQLException {
        String sql = "UPDATE Appointment SET patient_id = ?, doctor_id = ?, appointment_date = ?, appointment_time = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appt.getPatientId());
            ps.setInt(2, appt.getDoctorId());
            ps.setDate(3, appt.getDate());
            ps.setTime(4, appt.getTime());
            ps.setInt(5, appt.getId());
            ps.executeUpdate();
        }
    }

    // Delete appointment
    public void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM Appointment WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId); // FIXED — parameter name correct
            ps.executeUpdate();
        }
    }
}
