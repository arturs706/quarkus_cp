package com.example.usercomponent.users.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "superusers")
public class Superuser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "superuser_id")
    private UUID superuserId;

    @Column(name = "user_id", unique = true)
    private UUID userId;

    // Constructors

    public Superuser() {
        // Default constructor
    }

    // Getters and Setters

    public UUID getSuperuserId() {
        return superuserId;
    }

    public void setSuperuserId(UUID superuserId) {
        this.superuserId = superuserId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
