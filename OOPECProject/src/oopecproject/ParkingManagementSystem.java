/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopecproject;

/**
 *
 * @author Yusuf Salyani
 */


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class ParkingManagementSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:/parking_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    //private BasicDataSource dataSource;

    public ParkingManagementSystem() {
        createTablesIfNotExist();
        LocalDateTime currentDateTime = LocalDateTime.now(); // Get the current date and time
        deleteExpiredParkingPassesAndVehiclesFromDatabase(currentDateTime);
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void createTablesIfNotExist() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            // Create table for Student
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS student (" +
                            "ID VARCHAR(36) PRIMARY KEY," +
                            "name VARCHAR(255) NOT NULL" +
                            ")");
            // Create table for Vehicle
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS vehicle (" +
                            "ID VARCHAR(36) PRIMARY KEY," +
                            "license_plate VARCHAR(20) NOT NULL," +
                            "student_id VARCHAR(36) NOT NULL," +
                            "make VARCHAR(20) NOT NULL," +
                            "model VARCHAR(20) NOT NULL," +
                            "color VARCHAR(20) NOT NULL," +
                            "FOREIGN KEY (student_id) REFERENCES student(ID)" +
                            ")");
            // Create table for ParkingLot
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS parking_lot (" +
                            "ID VARCHAR(36) PRIMARY KEY," +
                            "name VARCHAR(255) NOT NULL," +
                            "capacity INT NOT NULL," +
                            "is_open BOOLEAN NOT NULL," +
                            "occupied_slots INT NOT NULL" +
                            ")");
            // Create table for ParkingSlot
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS parking_slot (" +
                            "ID VARCHAR(36) PRIMARY KEY," +
                            "number INT NOT NULL," +
                            "parking_lot_id VARCHAR(36) NOT NULL," +
                            "is_occupied BOOLEAN NOT NULL DEFAULT FALSE," +
                            "vehicle_id VARCHAR(36) DEFAULT NULL," +
                            "FOREIGN KEY (parking_lot_id) REFERENCES parking_lot(ID)," +
                            "FOREIGN KEY (vehicle_id) REFERENCES vehicle(ID)" +
                            ")");
            // Create table for ParkingPass
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS parking_pass (" +
                            "ID VARCHAR(36) PRIMARY KEY," +
                            "vehicle_id VARCHAR(36) NOT NULL," +
                            "valid_from TIMESTAMP NULL," +
                            "valid_to TIMESTAMP NULL," +
                            "FOREIGN KEY (vehicle_id) REFERENCES vehicle(ID)" +
                            ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods for Student class

    public void addStudent(Student student) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO student (ID, name) VALUES (?, ?)")) {
            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM student")) {
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String name = resultSet.getString("name");
                Student student = new Student(id, name);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Methods for Vehicle class

    public void addVehicle(Vehicle vehicle) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO vehicle (ID, license_plate, student_id, make, model, color) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, vehicle.getId());
            statement.setString(2, vehicle.getLicensePlate());
            statement.setString(3, vehicle.getStudentId());
            statement.setString(4, vehicle.getMake());
            statement.setString(5, vehicle.getModel());
            statement.setString(6, vehicle.getColor());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM vehicle")) {
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String licensePlate = resultSet.getString("license_plate");
                String studentId = resultSet.getString("student_id");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                Vehicle vehicle = new Vehicle(id, licensePlate, studentId, make, model, color);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
    
    public Vehicle getVehicleById(String vehicleId) {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(
                 "SELECT * FROM vehicle WHERE ID = ?")) {
        statement.setString(1, vehicleId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String id = resultSet.getString("ID");
            String licensePlate = resultSet.getString("license_plate");
            String studentId = resultSet.getString("student_id");
            String make = resultSet.getString("make");
            String model = resultSet.getString("model");
            String color = resultSet.getString("color");
            return new Vehicle(id, licensePlate, studentId, make, model, color);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    public Vehicle getVehicleByLicensePlate(String vehicleLicensePlate) {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(
                 "SELECT * FROM vehicle WHERE license_plate = ?")) {
        statement.setString(1, vehicleLicensePlate);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String id = resultSet.getString("ID");
            String licensePlate = resultSet.getString("license_plate");
            String studentId = resultSet.getString("student_id");
            String make = resultSet.getString("make");
            String model = resultSet.getString("model");
            String color = resultSet.getString("color");
            return new Vehicle(id, licensePlate, studentId, make, model, color);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    // Methods for ParkingLot class
    
    public void createParkingLot(ParkingLot parkingLot) {
    try (Connection connection = getConnection();
         PreparedStatement insertParkingLot = connection.prepareStatement(
                 "INSERT INTO parking_lot (ID, name, capacity, is_open, occupied_slots) VALUES (?, ?, ?, ?, ?)");
         PreparedStatement insertParkingSlot = connection.prepareStatement(
                 "INSERT INTO parking_slot (ID, number, parking_lot_id) VALUES (?, ?, ?)")) {

        // Set values for the parking lot insertion query
        insertParkingLot.setString(1, parkingLot.getId());
        insertParkingLot.setString(2, parkingLot.getName());
        insertParkingLot.setInt(3, parkingLot.getCapacity());
        insertParkingLot.setBoolean(4, parkingLot.isOpen());
        insertParkingLot.setInt(5, parkingLot.getOccupiedSlots());
        insertParkingLot.executeUpdate();

        // Insert parking slots for the given capacity
        for (int i = 1; i <= parkingLot.getCapacity(); i++) {
            ParkingSlot parkingSlot = new ParkingSlot(parkingLot.getId());
            insertParkingSlot.setString(1, parkingSlot.getId());
            insertParkingSlot.setInt(2, i);
            insertParkingSlot.setString(3, parkingLot.getId());
            insertParkingSlot.executeUpdate();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public void openParkingLot(ParkingLot parkingLot) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE parking_lot SET is_open = true WHERE ID = ?")) {
            statement.setString(1, parkingLot.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeParkingLot(ParkingLot parkingLot) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE parking_lot SET is_open = false WHERE ID = ?")) {
            statement.setString(1, parkingLot.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ParkingLot> getAllParkingLots() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM parking_lot")) {
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String name = resultSet.getString("name");
                int capacity = resultSet.getInt("capacity");
                boolean open = resultSet.getBoolean("is_open");
                int occupiedSlots = resultSet.getInt("occupied_slots");
                ParkingLot parkingLot = new ParkingLot(id, name, capacity, open);
                parkingLots.add(parkingLot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkingLots;
    }

    // Methods for ParkingSlot class

    public void addParkingSlot(ParkingLot parkingLot, ParkingSlot parkingSlot, Vehicle vehicle) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO parking_slot (ID, number, parking_lot_id, vehicle_id) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, parkingSlot.getId());
            statement.setInt(2, parkingSlot.getNumber());
            statement.setString(3, parkingLot.getId());
            statement.setString(4, vehicle.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //
    public ParkingSlot getParkingSlotByNumber(String parkingLot, int slotNumber) {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(
                 "SELECT * FROM parking_slot WHERE parking_lot_id = ? AND number = ?")) {
        statement.setString(1, parkingLot);
        statement.setInt(2, slotNumber);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String id = resultSet.getString("ID");
            int number = resultSet.getInt("number");
            String parkingLot_id = resultSet.getString("parking_lot_id");
            boolean isOccupied = resultSet.getBoolean("is_occupied");
            if (!isOccupied) {
                return new ParkingSlot(id, parkingLot_id, number, isOccupied);
            } else {
                String vehicleId = resultSet.getString("vehicle_id");
            Vehicle vehicle = getVehicleById(vehicleId);
            return new ParkingSlot(id, parkingLot_id, number, isOccupied, vehicle);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    //

    public void removeParkingSlot(ParkingLot parkingLot, ParkingSlot parkingSlot) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM parking_slot WHERE ID = ? AND parking_lot_id = ?")) {
            statement.setString(1, parkingSlot.getId());
            statement.setString(2, parkingLot.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignVehicleToSlot(ParkingSlot parkingSlot, Vehicle vehicle) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE parking_slot SET vehicle_id = ?, is_occupied = ? WHERE ID = ?")) {
            statement.setString(1, vehicle.getId());
            statement.setBoolean(2, true);
            statement.setString(3, parkingSlot.getId());
            
            //statement.setString(2, vehicle.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeParkingSlot(ParkingSlot parkingSlot) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE parking_slot SET vehicle_id = NULL, is_occupied = ? WHERE ID = ?")) {
            statement.setBoolean(1, false);
            statement.setString(2, parkingSlot.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ParkingSlot> getAllParkingSlots(ParkingLot parkingLot) {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM parking_slot WHERE parking_lot_id = ?  ORDER BY number ASC")) {
            statement.setString(1, parkingLot.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String parkingLotId = resultSet.getString("parking_lot_id");
                int number = resultSet.getInt("number");
                boolean isOccupied = resultSet.getBoolean("is_occupied");
                if (!isOccupied) {
                    ParkingSlot parkingSlot = new ParkingSlot(id, parkingLotId, number, isOccupied);
                    parkingSlots.add(parkingSlot);
                } else {
                    String vehicleId = resultSet.getString("vehicle_id");
                    Vehicle vehicle = getVehicleById(vehicleId);                
                    ParkingSlot parkingSlot = new ParkingSlot(id, parkingLotId, number, isOccupied, vehicle);
                    parkingSlots.add(parkingSlot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkingSlots;
    }
    
    public ParkingSlot getParkingSlotByVehicle(Vehicle vehicle) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM parking_slot WHERE vehicle_id = ?")) {
            statement.setString(1, vehicle.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString("ID");
                int number = resultSet.getInt("number");
                String parkingLot_id = resultSet.getString("parking_lot_id");
                boolean isOccupied = resultSet.getBoolean("is_occupied");
                String vehicleId = resultSet.getString("vehicle_id");
                Vehicle vehicle1 = getVehicleById(vehicleId);
                ParkingSlot parkingSlot = new ParkingSlot(id, parkingLot_id, number, isOccupied, vehicle1);
                return parkingSlot;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Methods for ParkingPass class

    public void addParkingPass(ParkingPass parkingPass) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO parking_pass (ID, vehicle_id, valid_from, valid_to) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, parkingPass.getId());
            statement.setString(2, parkingPass.getVehicle().getId());
            statement.setTimestamp(3, Timestamp.valueOf(parkingPass.getValidFrom()));
            statement.setTimestamp(4, Timestamp.valueOf(parkingPass.getValidTo()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ParkingPass> getAllParkingPasses() {
        List<ParkingPass> parkingPasses = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM parking_pass")) {
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String vehicleId = resultSet.getString("vehicle_id");
                LocalDateTime validFrom = resultSet.getTimestamp("valid_from").toLocalDateTime();
                LocalDateTime validTo = resultSet.getTimestamp("valid_to").toLocalDateTime();
                Vehicle vehicle = getVehicleById(vehicleId);
                ParkingPass parkingPass = new ParkingPass(id, vehicle, validFrom, validTo);
                parkingPasses.add(parkingPass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkingPasses;
    }
    
    public List<ParkingPass> getExpiredParkingPasses(LocalDateTime currentDateTime) {
    List<ParkingPass> expiredParkingPasses = new ArrayList<>();
    
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(
             "SELECT * FROM parking_pass WHERE valid_to < ?")) {
        statement.setTimestamp(1, Timestamp.valueOf(currentDateTime));
        
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String vehicleId = resultSet.getString("vehicle_id");
                Timestamp validFromTimestamp = resultSet.getTimestamp("valid_from");
                Timestamp validToTimestamp = resultSet.getTimestamp("valid_to");
                
                // Convert database timestamps to LocalDateTime
                LocalDateTime validFrom = validFromTimestamp.toLocalDateTime();
                LocalDateTime validTo = validToTimestamp.toLocalDateTime();
                
                // Retrieve the associated vehicle from the database
                Vehicle vehicle = getVehicleById(vehicleId);
                
                if (vehicle != null) {
                    ParkingPass parkingPass = new ParkingPass(id, vehicle, validFrom, validTo);
                    expiredParkingPasses.add(parkingPass);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return expiredParkingPasses;
}

    public void deleteExpiredParkingPassesAndVehiclesFromDatabase(LocalDateTime currentDateTime) {
    List<ParkingPass> expiredParkingPasses = getExpiredParkingPasses(currentDateTime);
    
    try (Connection connection = getConnection()) {
        connection.setAutoCommit(false); // Start a transaction
        
        try (PreparedStatement deleteParkingPassStatement = connection.prepareStatement(
                 "DELETE FROM parking_pass WHERE ID = ?");
             PreparedStatement deleteVehicleStatement = connection.prepareStatement(
                 "DELETE FROM vehicle WHERE ID = ?")) {
            
            for (ParkingPass parkingPass : expiredParkingPasses) {
                // Delete parking pass from the database
                deleteParkingPassStatement.setString(1, parkingPass.getId());
                deleteParkingPassStatement.executeUpdate();
                
                // Delete associated vehicle from the database
                deleteVehicleStatement.setString(1, parkingPass.getVehicle().getId());
                deleteVehicleStatement.executeUpdate();
            }
            
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    public ParkingPass generateParkingPass(Vehicle vehicle) {
        LocalDateTime validFrom = LocalDateTime.now();
        LocalDateTime validTo = validFrom.plusMonths(12);

        return new ParkingPass(vehicle, validFrom, validTo);
}
}

