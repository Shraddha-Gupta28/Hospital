package com.dao;

import com.model.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao {
    private Connection conn;

    public DoctorDao(Connection conn) {
        this.conn = conn;
    }

    // Create - Add new doctor
    public void addDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctor (id, name, specialization) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctor.getId());
            stmt.setString(2, doctor.getName());
            stmt.setString(3, doctor.getSpecialization());
            stmt.executeUpdate();
        }
    }
    
 // Get list of doctors by specialization
    public List<Doctor> getDoctorsBySpecialization(String specialization) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor WHERE specialization = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, specialization);
            ResultSet rs = stmt.executeQuery();
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
    

    // Get doctor by ID
    public Doctor getDoctorById(int id) throws SQLException {
        String sql = "SELECT * FROM doctor WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Doctor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization")
                );
            }
            return null;
        }
    }

    //Get all doctors
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
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
    

    // Update existing doctor
    public void updateDoctor(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctor SET name = ?, specialization = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getSpecialization());
            stmt.setInt(3, doctor.getId());
            stmt.executeUpdate();
        }
    }

    // Delete doctor ID
    public void deleteDoctor(int id) throws SQLException {
        String sql = "DELETE FROM doctor WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    // Total count of Doctor
    public int getDoctorCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM doctor";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
}
