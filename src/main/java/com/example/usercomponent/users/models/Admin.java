package com.example.usercomponent.users.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private UUID adminId;

    @Column(name = "user_id", unique = true)
    private UUID userId;

    // Constructors

    public Admin() {
        // Default constructor
    }

    // Getters and Setters

    public UUID getAdminId() {
        return adminId;
    }

    public void setAdminId(UUID adminId) {
        this.adminId = adminId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
