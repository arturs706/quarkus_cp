package com.example.usercomponent.users.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @Column(name = "artist_id", length = 40, nullable = false)
    private UUID artistId;

    @Column(name = "user_id", length = 100, nullable = false)
    private UUID user_id;

    @Column(name = "artist_name", length = 100, nullable = false)
    private String artistName;

    @Column(name = "validated", nullable = false)
    private boolean validated;

    // Constructors

    public Artist() {
        // Default constructor
    }

    public Artist(String artistName, boolean validated, UUID user_id) {
        this.artistId = UUID.randomUUID();
        this.artistName = artistName;
        this.validated = validated;
        this.user_id = user_id;

    }

    // Getters and Setters

    public UUID getArtistId() {
        return artistId;
    }

    public void setArtistId(UUID artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public UUID getUserId() {
        return user_id;
    }

    public void setUserId(UUID user_id) {
        this.user_id = user_id;
    }
}
