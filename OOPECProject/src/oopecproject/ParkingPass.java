/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopecproject;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Yusuf Salyani
 */


import java.time.LocalDateTime;
import java.util.UUID;

public class ParkingPass {
    private String id;
    private Vehicle vehicle;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;

    public ParkingPass(Vehicle vehicle, LocalDateTime validFrom, LocalDateTime validTo) {
        this.id = generateUniqueID();
        this.vehicle = vehicle;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public ParkingPass(String id, Vehicle vehicle, LocalDateTime validFrom, LocalDateTime validTo) {
        this.id = id;
        this.vehicle = vehicle;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    // ... Getters and setters ...
    
    public String getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    private String generateUniqueID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}