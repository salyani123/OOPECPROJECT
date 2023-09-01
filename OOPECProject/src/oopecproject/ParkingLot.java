/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopecproject;

/**
 *
 * @author Yusuf Salyani
 */


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParkingLot {
    private String id;
    private String name;
    private int capacity;
    private int occupiedSlots;
    private boolean isOpen;
    

    private List<ParkingSlot> parkingSlots;

    public ParkingLot(String name, int capacity) {
        this.id = generateUniqueID();
        this.name = name;
        this.capacity = capacity;
        this.occupiedSlots = 0;
        this.isOpen = false;
        this.parkingSlots = new ArrayList<>();
    }

    public ParkingLot(String id, String name, int capacity, boolean open) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.occupiedSlots = 0;
        this.isOpen = open;
        this.parkingSlots = new ArrayList<>();
    }

    // ... Getters and setters ...
    
    public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getOccupiedSlots() {
            return occupiedSlots;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

    private String generateUniqueID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void addParkingSlot(ParkingSlot parkingSlot) {
        parkingSlots.add(parkingSlot);
    }

    public void removeParkingSlot(ParkingSlot parkingSlot) {
        parkingSlots.remove(parkingSlot);
    }
}