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

public class Student {
    private String id;
    private String name;

    public Student(String name) {
        this.id = generateUniqueID();
        this.name = name;
    }

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // ... Getters and setters ...
    
    public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    private String generateUniqueID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
