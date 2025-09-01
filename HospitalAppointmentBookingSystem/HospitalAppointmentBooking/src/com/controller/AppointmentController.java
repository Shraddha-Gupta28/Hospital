package com.controller;

import com.dao.AppointmentDao;
import com.model.Appointment;
import com.model.Doctor;
import com.model.Patient;
import com.service.AppointmentService;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class AppointmentController {
    private AppointmentService appointmentService;
    private Scanner sc;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n===== Appointment Booking =====");
            System.out.println("1. Register New Patient");
            System.out.println("2. View All Doctors");
            System.out.println("3. Book Appointment");
            System.out.println("4. View Appointment History");
            System.out.println("5. Delete Appointment");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> registerPatient();
                case 2 -> viewAllDoctors();
                case 3 -> bookAppointment();
                case 4 -> viewAppointmentHistory();
                case 5 -> deleteAppointment();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }

    private void registerPatient() {
        try {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Age: ");
            int age = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Gender: ");
            String gender = sc.nextLine();

            Patient patient = new Patient(0, name, age, gender);
            appointmentService.registerPatient(patient);
            System.out.println("✅ Patient registered successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void viewAllDoctors() {
        try {
            List<Doctor> doctors = appointmentService.getAllDoctors();
            if (doctors.isEmpty()) {
                System.out.println("No doctors found.");
                return;
            }
            System.out.println("--- Doctors List ---");
            doctors.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void bookAppointment() {
        try {
            System.out.print("Enter Patient ID: ");
            int patientId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Doctor ID: ");
            int doctorId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Appointment Date (yyyy-mm-dd): ");
            String dateStr = sc.nextLine();
            Date date = Date.valueOf(dateStr);

            System.out.print("Enter Appointment Time (HH:mm:ss): ");
            String timeStr = sc.nextLine();
            Time time = Time.valueOf(timeStr);

            Appointment appt = new Appointment(0, patientId, doctorId, date, time);
            appointmentService.bookAppointment(appt);
            System.out.println("✅ Appointment booked!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void viewAppointmentHistory() {
        try {
            List<Appointment> history = appointmentService.getAppointmentHistory();
            if (history.isEmpty()) {
                System.out.println("No appointments found.");
                return;
            }
            System.out.println("--- Appointment History ---");
            history.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void deleteAppointment() {
        try {
            System.out.print("Enter Appointment ID to delete: ");
            int id = Integer.parseInt(sc.nextLine());
            appointmentService.deleteAppointment(id);
            System.out.println("✅ Appointment deleted!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "password");
        AppointmentDao dao = new AppointmentDao(conn);
        AppointmentService service = new AppointmentService(dao);
        AppointmentController controller = new AppointmentController(service);
        controller.start();
    }
}
