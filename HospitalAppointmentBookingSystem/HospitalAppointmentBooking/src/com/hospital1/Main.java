package com.hospital1;

import com.controller.AppointmentController;
import com.controller.DoctorController;
import com.controller.PatientController;
import com.service.AppointmentService;
import com.dao.AppointmentDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;

        try {
            // Load JDBC driver and establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "70240");

            // Controllers and Services
            PatientController patientController = new PatientController(); 
            DoctorController doctorController = new DoctorController(null);
            AppointmentDao appointmentDao = new AppointmentDao(conn);
            AppointmentService appointmentService = new AppointmentService(appointmentDao);
            AppointmentController appointmentController = new AppointmentController(appointmentService);

            while (true) {
                System.out.println("\n=== Hospital Management System ===");
                System.out.println("1. Patient Operations");
                System.out.println("2. Doctor Operations");
                System.out.println("3. Appointment Operations");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        patientMenu(patientController, scanner);
                        break;
                    case "2":
                        doctorMenu(doctorController, scanner);
                        break;
                    case "3":
                        appointmentController.start();  // Uses internal menu for appointments
                        break;
                    case "4":
                        System.out.println("Thank you for using the Hospital Management System!");
                        scanner.close();
                        if (conn != null) conn.close();
                        return;
                    default:
                        System.out.println("Invalid choice! Please enter again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Just in case connection 
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Patient menu
    private static void patientMenu(PatientController patientController, Scanner scanner) throws Exception {
        while(true) {
            System.out.println("\n--- Patient Menu ---");
            System.out.println("1. List Patients");
            System.out.println("2. Add Patient");
            System.out.println("3. Delete Patient");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            if ("1".equals(option)) {
                for (var patient : patientController.listPatients()) {
                    System.out.printf("ID: %d, Name: %s, Age: %d, Gender: %s%n",
                        patient.getId(), patient.getName(), patient.getAge(), patient.getGender());
                }
            } else if ("2".equals(option)) {
                System.out.print("Enter patient ID: ");
                int id = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter patient name: ");
                String name = scanner.nextLine();

                System.out.print("Enter patient age: ");
                int age = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter patient gender: ");
                String gender = scanner.nextLine();

                // Create patient object and add
                var patient = new com.model.Patient(); // Assuming you have a default constructor
                patient.setId(id);
                patient.setName(name);
                patient.setAge(age);
                patient.setGender(gender);

                patientController.addPatient(patient);
                System.out.println("Patient added successfully.");

            } else if ("3".equals(option)) {
                System.out.print("Enter patient ID to delete: ");
                int id = Integer.parseInt(scanner.nextLine());
                patientController.deletePatient(id);
                System.out.println("Patient deleted successfully.");

            } else if ("4".equals(option)) {
                break;
            } else {
                System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Doctor menu
    private static void doctorMenu(DoctorController doctorController, Scanner scanner) throws Exception {
        while (true) {
            System.out.println("\n--- Doctor Menu ---");
            System.out.println("1. List Doctors");
            System.out.println("2. Add Doctor");
            System.out.println("3. Delete Doctor");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            if ("1".equals(option)) {
                doctorController.addDoctor();
            } else if ("2".equals(option)) {
                System.out.print("Enter doctor ID: ");
                int id = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter doctor name: ");
                String name = scanner.nextLine();

                System.out.print("Enter specialization: ");
                String specialization = scanner.nextLine();

                var doctor = new com.model.Doctor(id, name, specialization);
                doctorController.addDoctor();

            } else if ("3".equals(option)) {
                System.out.print("Enter doctor ID to delete: ");
                int id = Integer.parseInt(scanner.nextLine());
                doctorController.deleteDoctor();

            } else if ("4".equals(option)) {
                break;
            } else {
                System.out.println("Invalid option, please try again.");
            }
        }
    }
}
