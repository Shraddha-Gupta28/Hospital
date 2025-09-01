package com.controller;

import com.dao.DoctorDao;
import com.model.Doctor;
import com.service.DoctorService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DoctorController {
    private DoctorService doctorService;
    private Scanner sc;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n===== Doctor Management =====");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctor By ID");
            System.out.println("3. View Doctors By Specialization");
            System.out.println("4. View All Doctors");
            System.out.println("5. Update Doctor");
            System.out.println("6. Delete Doctor");
            System.out.println("7. Get Doctor Count");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addDoctor();
                case 2 -> viewDoctorById();
                case 3 -> viewDoctorsBySpecialization();
                case 4 -> viewAllDoctors();
                case 5 -> updateDoctor();
                case 6 -> deleteDoctor();
                case 7 -> getDoctorCount();
                case 8 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice, try again!");
            }
        }
    }

    public void addDoctor() {
        try {
            System.out.print("Enter Doctor Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Specialization: ");
            String specialization = sc.nextLine();

            Doctor doctor = new Doctor(0, name, specialization);
            doctorService.addDoctor(doctor);
            System.out.println("✅ Doctor added successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public void viewDoctorById() {
        try {
            System.out.print("Enter Doctor ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            Doctor doctor = doctorService.getDoctorById(id);
            if (doctor != null) {
                System.out.println(doctor);
            } else {
                System.out.println("❌ Doctor not found.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void viewDoctorsBySpecialization() {
        try {
            System.out.print("Enter Specialization: ");
            String specialization = sc.nextLine();
            List<Doctor> doctors = doctorService.getDoctorsBySpecialization(specialization);
            if (doctors != null && !doctors.isEmpty()) {
                doctors.forEach(System.out::println);
            } else {
                System.out.println("❌ No doctors found with this specialization.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public void viewAllDoctors() {
        try {
            List<Doctor> doctors = doctorService.getAllDoctors();
            if (doctors != null && !doctors.isEmpty()) {
                doctors.forEach(System.out::println);
            } else {
                System.out.println("❌ No doctors found.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void updateDoctor() {
        try {
            System.out.print("Enter Doctor ID to update: ");
            int id = sc.nextInt(); sc.nextLine();
            System.out.print("Enter New Name: ");
            String name = sc.nextLine();
            System.out.print("Enter New Specialization: ");
            String specialization = sc.nextLine();

            Doctor doctor = new Doctor(id, name, specialization);
            doctorService.updateDoctor(doctor);
            System.out.println("✅ Doctor updated successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public void deleteDoctor() {
        try {
            System.out.print("Enter Doctor ID to delete: ");
            int id = sc.nextInt();
            sc.nextLine();
            doctorService.deleteDoctor(id);
            System.out.println("✅ Doctor deleted successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void getDoctorCount() {
        try {
            int count = doctorService.getDoctorCount();
            System.out.println("Total Doctors: " + count);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    

}

