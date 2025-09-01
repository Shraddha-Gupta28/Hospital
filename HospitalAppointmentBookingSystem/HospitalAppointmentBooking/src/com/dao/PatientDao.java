package com.dao;

import com.model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDao {
    private Connection conn;

    public PatientDao(Connection conn) {
        this.conn = conn;
    }

    //  Insert patient
    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patient (id, name, age, gender) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patient.getId());
            stmt.setString(2, patient.getName());
            stmt.setInt(3, patient.getAge());
            stmt.setString(4, patient.getGender());
            stmt.executeUpdate();
        }
    }

    // get patient Id
    public Patient getPatientById(int id) throws SQLException {
        String sql = "SELECT * FROM patient WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender")
                );
            }
            return null;
        }
    }
    
       
 // Get list of patients within an age range (inclusive)
    public List<Patient> getPatientsByAgeRange(int minAge, int maxAge) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE age BETWEEN ? AND ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, minAge);
            stmt.setInt(2, maxAge);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                patients.add(new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender")
                ));
            }
        }
        return patients;
    }

    
 // Get list of patients by gender
    public List<Patient> getPatientsByGender(String gender) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE gender = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gender);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                patients.add(new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender")
                ));
            }
        }
        return patients;
    }

    

    //Get all patients
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                patients.add(new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender")
                ));
            }
        }
        return patients;
    }

    // Update patient
    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patient SET name = ?, age = ?, gender = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender());
            stmt.setInt(4, patient.getId());
            stmt.executeUpdate();
        }
    }

    // Delete patient
    public void deletePatient(int id) throws SQLException {
        String sql = "DELETE FROM patient WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
