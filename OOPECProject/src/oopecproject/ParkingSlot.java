/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopecproject;

import java.util.UUID;

/**
 *
 * @author Yusuf Salyani
 */


public class ParkingSlot {
    private String id;
    private String parkingLot;
    private int number;
    private boolean isOccupied;
    private Vehicle vehicle;
    

    public ParkingSlot(int number, boolean isOccupied, Vehicle vehicle) {
        this.id = generateUniqueID();
        this.number = number;
        this.isOccupied = isOccupied;
        this.vehicle = vehicle;
    }
    
    public ParkingSlot(String parkingLot){
        this.id = generateUniqueID();
        this.parkingLot = parkingLot;
        this.isOccupied = false;
    }
    
    public ParkingSlot(String parkingLot, int number, Vehicle vehicle) {
        this.id = generateUniqueID();
        this.parkingLot = parkingLot;
        this.number = number;
        this.isOccupied = true;
        this.vehicle = vehicle;
    }

    public ParkingSlot(String id, String parkingLot, int number, boolean isOccupied, Vehicle vehicle) {
        this.id = id;
        this.parkingLot = parkingLot;
        this.number = number;
        this.isOccupied = isOccupied;
        this.vehicle = vehicle;
    }
    
    
    
    public ParkingSlot(String id, String parkingLot, int number, boolean isOccupied) {
        this.id = id;
        this.parkingLot = parkingLot;
        this.number = number;
        this.isOccupied = isOccupied;
    }

    // ... Getters and setters ...
    
    public String getId() {
            return id;
        }

        public int getNumber() {
            return number;
        }
        
        public String getParkingLotId() {
            return parkingLot;
        }

        public boolean isOccupied() {
            return isOccupied;
        }

        public void setOccupied(boolean occupied) {
            isOccupied = occupied;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public void setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
        }

    private String generateUniqueID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
