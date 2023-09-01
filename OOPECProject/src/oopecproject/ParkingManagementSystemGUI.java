/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopecproject;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Yusuf Salyani
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ParkingManagementSystemGUI extends JFrame {
    private ParkingManagementSystem parkingSystem;

    private JButton addStudentButton;
    private JButton addVehicleButton;
    private JButton viewAllVehiclesButton;
    private JButton viewAllParkingPassesButton;
    private JButton createParkingLotButton;
    private JButton viewAllParkingLotsButton;
    private JButton viewAllParkingSlotsButton;
    private JButton checkInVehicleButton;
    private JButton checkOutVehicleButton;
    private JButton openCloseParkingLotButton;

    public ParkingManagementSystemGUI() {
        parkingSystem = new ParkingManagementSystem();

        setTitle("Parking Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 500));
        setLayout(new GridLayout(2, 5, 10, 10));
        //setBorder(new EmptyBorder(20, 20, 20, 20));

        addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = JOptionPane.showInputDialog(null, "Enter student name:");
                if (studentName != null && !studentName.isEmpty()) {
                    Student student = new Student(studentName);
                    parkingSystem.addStudent(student);
                    JOptionPane.showMessageDialog(null, "Student added successfully!");
                }
            }
        });

        addVehicleButton = new JButton("Add Vehicle");
        addVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = JOptionPane.showInputDialog(null, "Enter student ID:");
                String licensePlate = JOptionPane.showInputDialog(null, "Enter vehicle license plate:");
                String make = JOptionPane.showInputDialog(null, "Enter vehicle make:");
                String model = JOptionPane.showInputDialog(null, "Enter vehicle model:");
                String color = JOptionPane.showInputDialog(null, "Enter vehicle color:");
                if (studentId != null && !studentId.isEmpty() && licensePlate != null && !licensePlate.isEmpty()) {
                    Vehicle vehicle = new Vehicle(licensePlate, studentId, make, model, color);
                    parkingSystem.addVehicle(vehicle);
                    ParkingPass parkingPass = parkingSystem.generateParkingPass(vehicle);
                    parkingSystem.addParkingPass(parkingPass);
                    JOptionPane.showMessageDialog(null, "Vehicle added successfully!");
                }
            }
        });
        
        viewAllVehiclesButton = new JButton("View All Vehicles");
        viewAllVehiclesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Vehicle> vehicles = parkingSystem.getAllVehicles();
                showVehicleTableDialog(vehicles);
            }
        });
        
        viewAllParkingPassesButton = new JButton("View Parking Passes");
        viewAllParkingPassesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ParkingPass> parkingPasses = parkingSystem.getAllParkingPasses();
                showParkingPassTableDialog(parkingPasses);
            }
        });


        createParkingLotButton = new JButton("Create Parking Lot");
        createParkingLotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parkingLotName = JOptionPane.showInputDialog(null, "Enter parking lot name:");
                String capacityStr = JOptionPane.showInputDialog(null, "Enter parking lot capacity:");
                if (parkingLotName != null && !parkingLotName.isEmpty() && capacityStr != null && !capacityStr.isEmpty()) {
                    int capacity = Integer.parseInt(capacityStr);
                    ParkingLot parkingLot = new ParkingLot(parkingLotName, capacity);
                    parkingSystem.createParkingLot(parkingLot);
                    JOptionPane.showMessageDialog(null, "Parking lot created successfully!");
                }
            }
        });
        
        viewAllParkingLotsButton = new JButton("View All Parking Lots");
        viewAllParkingLotsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ParkingLot> parkingLots = parkingSystem.getAllParkingLots();
                showParkingLotTableDialog(parkingLots);
            }
        });
        
        viewAllParkingSlotsButton = new JButton("View Parking Slots for Parking Lot");
        viewAllParkingSlotsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parkingLotId = JOptionPane.showInputDialog(null, "Enter parking lot ID:");
                ParkingLot parkingLot = getParkingLotById(parkingLotId);

                if (parkingLot != null) {
                    List<ParkingSlot> parkingSlots = parkingSystem.getAllParkingSlots(parkingLot);
                    showParkingSlotTableDialog(parkingSlots);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid parking lot ID!");
                }
            }
        });

        checkInVehicleButton = new JButton("Check In Vehicle");
        checkInVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parkingLotId = JOptionPane.showInputDialog(null, "Enter parking lot ID:");
                String slotNumberStr = JOptionPane.showInputDialog(null, "Enter parking slot number:");
                if (parkingLotId != null && !parkingLotId.isEmpty() && slotNumberStr != null && !slotNumberStr.isEmpty()) {
                    int slotNumber = Integer.parseInt(slotNumberStr);
                    ParkingSlot parkingSlot = parkingSystem.getParkingSlotByNumber(parkingLotId, slotNumber);
                    if (parkingSlot != null) {
                        String vehicleLicensePlate = JOptionPane.showInputDialog(null, "Enter vehicle license plate:");
                        Vehicle vehicle = parkingSystem.getVehicleByLicensePlate(vehicleLicensePlate);
                        parkingSystem.assignVehicleToSlot(parkingSlot, vehicle);
                        JOptionPane.showMessageDialog(null, "Vehicle checked in successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid parking slot number or parking lot ID!");
                    }
                }
            }
        });

        checkOutVehicleButton = new JButton("Check Out Vehicle");
        checkOutVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vehicleLicensePlate = JOptionPane.showInputDialog(null, "Enter vehicle license plate:");
                Vehicle vehicle = parkingSystem.getVehicleByLicensePlate(vehicleLicensePlate);
                if (vehicle != null) {
                    ParkingSlot parkingSlot = parkingSystem.getParkingSlotByVehicle(vehicle);
                    if (parkingSlot != null) {
                        parkingSystem.freeParkingSlot(parkingSlot);
                        JOptionPane.showMessageDialog(null, "Vehicle checked out successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Vehicle is not checked in!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vehicle not found!");
                }
            }
        });

        openCloseParkingLotButton = new JButton("Open/Close Parking Lot");
        openCloseParkingLotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parkingLotId = JOptionPane.showInputDialog(null, "Enter parking lot ID:");
                ParkingLot parkingLot = getParkingLotById(parkingLotId);
                if (parkingLot != null) {
                    String openStatus = parkingLot.isOpen() ? "open" : "closed";
                    int choice = JOptionPane.showConfirmDialog(null,
                            "Parking lot is currently " + openStatus + ". Do you want to " +
                                    (parkingLot.isOpen() ? "close" : "open") + " it?", "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        if (parkingLot.isOpen()) {
                            parkingSystem.closeParkingLot(parkingLot);
                        } else {
                            parkingSystem.openParkingLot(parkingLot);
                        }
                        JOptionPane.showMessageDialog(null, "Parking lot status updated successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid parking lot ID!");
                }
            }
        });

        add(addStudentButton);
        add(addVehicleButton);
        add(viewAllVehiclesButton);
        add(viewAllParkingPassesButton);
        add(createParkingLotButton);
        add(viewAllParkingLotsButton);
        add(viewAllParkingSlotsButton);
        add(checkInVehicleButton);
        add(checkOutVehicleButton);
        add(openCloseParkingLotButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private ParkingLot getParkingLotById(String parkingLotId) {
        List<ParkingLot> parkingLots = parkingSystem.getAllParkingLots();
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getId().equals(parkingLotId)) {
                return parkingLot;
            }
        }
        return null;
    }

    private Vehicle getVehicleByLicensePlate(String licensePlate) {
        List<Vehicle> vehicles = parkingSystem.getAllVehicles();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate().equals(licensePlate)) {
                return vehicle;
            }
        }
        return null;
    }
    
    private void showVehicleTableDialog(List<Vehicle> vehicles) {
        JFrame vehicleFrame = new JFrame("All Vehicles");
        vehicleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vehicleFrame.setSize(800, 400);

        String[] columnNames = {"ID", "License Plate", "Student ID", "Make", "Model", "Color"};
        String[][] data = new String[vehicles.size()][6];

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            data[i][0] = vehicle.getId();
            data[i][1] = vehicle.getLicensePlate();
            data[i][2] = vehicle.getStudentId();
            data[i][3] = vehicle.getMake();
            data[i][4] = vehicle.getModel();
            data[i][5] = vehicle.getColor();
        }

        JTable vehicleTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(vehicleTable);

        vehicleFrame.add(scrollPane);
        vehicleFrame.setVisible(true);
    }
    
    private void showParkingLotTableDialog(List<ParkingLot> parkingLots) {
        JFrame parkingLotFrame = new JFrame("All Parking Lots");
        parkingLotFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        parkingLotFrame.setSize(800, 400);

        String[] columnNames = {"ID", "Name", "Capacity", "Open"};
        String[][] data = new String[parkingLots.size()][4];

        for (int i = 0; i < parkingLots.size(); i++) {
            ParkingLot parkingLot = parkingLots.get(i);
            data[i][0] = parkingLot.getId();
            data[i][1] = parkingLot.getName();
            data[i][2] = String.valueOf(parkingLot.getCapacity());
            data[i][3] = parkingLot.isOpen() ? "Open" : "Closed";
        }

        JTable parkingLotTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(parkingLotTable);

        parkingLotFrame.add(scrollPane);
        parkingLotFrame.setVisible(true);
    }
    
    private void showParkingSlotTableDialog(List<ParkingSlot> parkingSlots) {
        JFrame parkingSlotFrame = new JFrame("Parking Slots for Parking Lot");
        parkingSlotFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        parkingSlotFrame.setSize(800, 400);

        String[] columnNames = {"ID", "Parking Lot ID", "Number", "Is Open", "Vehicle ID"};
        String[][] data = new String[parkingSlots.size()][5];

        for (int i = 0; i < parkingSlots.size(); i++) {
            ParkingSlot parkingSlot = parkingSlots.get(i);
            data[i][0] = parkingSlot.getId();
            data[i][1] = parkingSlot.getParkingLotId();
            data[i][2] = String.valueOf(parkingSlot.getNumber());
            data[i][3] = parkingSlot.isOccupied() ? "Occupied" : "Free";
            if (parkingSlot.getVehicle() != null) {
                String vehicleId = parkingSlot.getVehicle().getLicensePlate();
                data[i][4] = vehicleId;
            } else {
                data[i][4] = "N/A"; // Or any appropriate placeholder for no vehicle
            }            
        }

        JTable parkingSlotTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(parkingSlotTable);

        parkingSlotFrame.add(scrollPane);
        parkingSlotFrame.setVisible(true);
    }
    
    private void showParkingPassTableDialog(List<ParkingPass> parkingPasses) {
        JFrame parkingPassFrame = new JFrame("All Parking Passes");
        parkingPassFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        parkingPassFrame.setSize(800, 400);

        String[] columnNames = {"ID", "Vehicle License Plate", "Valid From", "Valid To"};
        String[][] data = new String[parkingPasses.size()][4];

        for (int i = 0; i < parkingPasses.size(); i++) {
            ParkingPass parkingPass = parkingPasses.get(i);
            data[i][0] = parkingPass.getId();
            data[i][1] = parkingPass.getVehicle().getLicensePlate();
            data[i][2] = parkingPass.getValidFrom().toString();
            data[i][3] = parkingPass.getValidTo().toString();
        }

        JTable parkingPassTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(parkingPassTable);

        parkingPassFrame.add(scrollPane);
        parkingPassFrame.setVisible(true);
    }


}