package com.controller;

import com.dao.DoctorDao;
import com.model.Doctor;
import com.service.DoctorService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;

public class DoctorController {

    private DoctorService doctorService;

    public DoctorController() throws Exception {
    	
        // Database connection 
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/hospital", "root", "70240");

        DoctorDao doctorDao = new DoctorDao(conn);
        doctorService = new DoctorService(doctorDao);
    }

    //  add doctor
    public void addDoctor(Doctor doctor) throws Exception {
        doctorService.addDoctor(doctor);
        System.out.println("Doctor added successfully!");
    }

    // CLI method to list all doctors
    public void listDoctors() throws Exception {
        List<Doctor> doctors = doctorService.getAllDoctors();
        System.out.println("Doctors List:");
        for (Doctor d : doctors) {
            System.out.printf("ID: %d, Name: %s, Specialization: %s%n", d.getId(), d.getName(), d.getSpecialization());
        }
    }

    // CLI method to delete doctor by id
    public void deleteDoctor(int id) throws Exception {
        doctorService.deleteDoctor(id);
        System.out.println("Doctor deleted successfully!");
    }

    public static void main(String[] args) {
        try {
            DoctorController controller = new DoctorController();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nChoose an option: 1:Add Doctor  2:List Doctors  3:Delete Doctor  4:Exit");
                int option = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (option == 1) {
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Specialization: ");
                    String specialization = scanner.nextLine();

                    Doctor doctor = new Doctor(id, name, specialization);
                    controller.addDoctor(doctor);

                } else if (option == 2) {
                    controller.listDoctors();

                } else if (option == 3) {
                    System.out.print("Enter ID to delete: ");
                    int id = scanner.nextInt();
                    controller.deleteDoctor(id);

                } else if (option == 4) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid option, try again.");
                }
            }

           

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

