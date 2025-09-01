package com.controller;

import com.dao.AppointmentDao;
import com.model.Appointment;
import com.model.Patient;
import com.model.Doctor;
import com.service.AppointmentService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AppointmentController {
    private AppointmentService service;
    private Scanner sc;

    public AppointmentController(AppointmentService service) {
        this.service = service;
        this.sc = new Scanner(System.in);
    }

    // Menu loop
    public void start() {
        while (true) {
            System.out.println("\n===== Appointment Management =====");
            System.out.println("1. Create Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. View Appointment By ID");
            System.out.println("4. Update Appointment");
            System.out.println("5. Delete Appointment");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
            case 1:
                createAppointment();
                break;
            case 2:
                viewAllAppointments();
                break;
            case 3:
                viewAppointmentById();
                break;
            case 4:
                updateAppointment();
                break;
            case 5:
                deleteAppointment();
                break;
            case 6:
                System.out.println("Exiting....");
                return; 
            default:
                System.out.println("Invalid choice! Try again.");
                break; 
        }

        System.out.println(); 
    }
}
        
    private void createAppointment() {
        System.out.print("Enter Patient ID: ");
        int pid = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Patient Name: ");
        String pname = sc.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Patient Gender: ");
        String gender = sc.nextLine();
        Patient p = new Patient(pid, pname, age, gender);

        System.out.print("Enter Doctor ID: ");
        int did = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Doctor Name: ");
        String dname = sc.nextLine();
        System.out.print("Enter Doctor Specialization: ");
        String specialization = sc.nextLine();
        Doctor d = new Doctor(did, dname, specialization);

        System.out.print("Enter Reason for Appointment: ");
        String reason = sc.nextLine();

        LocalDateTime dateTime = LocalDateTime.now();

        Appointment appt = new Appointment(0, p, d, dateTime, reason);

        if (service.createAppointment(appt))
            System.out.println(" Appointment Created");
        else
            System.out.println(" Failed to create appointment");
    }

    private void viewAllAppointments() {
        List<Appointment> list = service.getAllAppointments();
        if (list != null && !list.isEmpty()) {
            for (Appointment a : list) {
                System.out.println(a);
            }
        } else {
            System.out.println("No appointments found.");
        }
    }
 

    private void viewAppointmentById() {
        System.out.print("Enter Appointment ID: ");
        int id = sc.nextInt();
        Appointment appt = service.getAppointmentById(id);
        if (appt != null) {
            System.out.println(appt);
        } else {
            System.out.println("Appointment not found.");
        }
    }

    private void updateAppointment() {
        System.out.print("Enter Appointment ID to update: ");
        int id = sc.nextInt(); sc.nextLine();

        Appointment existing = service.getAppointmentById(id);
        if (existing == null) {
            System.out.println("Appointment not found!");
            return;
        }

        System.out.print("Enter new Reason: ");
        String reason = sc.nextLine();
        existing.setReason(reason);

        if (service.updateAppointment(existing))
            System.out.println(" Appointment Updated");
        else
            System.out.println(" Failed to update appointment");
    }

    private void deleteAppointment() {
        System.out.print("Enter Appointment ID to delete: ");
        int id = sc.nextInt();
        if (service.deleteAppointment(id))
            System.out.println(" Appointment Deleted: ");
        else
            System.out.println(" Failed to delete appointment: ");
    }

    // Main method to run the controller
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "password");

        AppointmentDao dao = new AppointmentDao(conn);
        AppointmentService service = new AppointmentService(dao);
        AppointmentController controller = new AppointmentController(service);
        controller.start();
    }
}
