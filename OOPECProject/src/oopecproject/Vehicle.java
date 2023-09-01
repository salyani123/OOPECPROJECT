/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopecproject;

/**
 *
 * @author Yusuf Salyani
 */

import java.util.UUID;

public class Vehicle {
    private String id;
    private String licensePlate;
    private String studentId;
    private String make;
    private String model;
    private String color;
    

    public Vehicle(String licensePlate, String studentId, String make, String model, String color) {
        this.id = generateUniqueID();
        this.licensePlate = licensePlate;
        this.studentId = studentId;
        this.make = make;
        this.model = model;
        this.color = color;
    }

    public Vehicle(String id, String licensePlate, String studentId, String make, String model, String color) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.studentId = studentId;
        this.make = make;
        this.model = model;
        this.color = color;
    }
    
    public Vehicle(String vehicleLicensePlate) {
        
    }

    // ... Getters and setters ...
    
    public String getId() {
            return id;
        }

        public String getLicensePlate() {
            return licensePlate;
        }

        public void setLicensePlate(String registrationNumber) {
            this.licensePlate = registrationNumber;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String ownerId) {
            this.studentId = ownerId;
        }

    private String generateUniqueID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}