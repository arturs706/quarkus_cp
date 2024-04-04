package com.example.songcomponent.song.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @Column(name = "song_id", length = 40, nullable = false)
    private UUID songId;

    @Column(name = "artist_id", nullable = false)
    private UUID artistId;

    @Column(name = "song_title", length = 255, nullable = false)
    private String songTitle;

    @Column(name = "album_name", length = 255)
    private String albumName;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "genre", length = 100, nullable = false)
    private String genre;

    @Column(name = "country_of_origin", length = 100, nullable = false)
    private String countryOfOrigin;

    @Column(name = "file_path", length = 255, nullable = false)
    private String filePath;

    @Column(name = "played_times", nullable = false)
    private int playedTimes;

    @Column(name = "image_path", length = 150)
    private String image_path;

    // Constructors

    public Song() {
        this.songId = UUID.randomUUID();
    }

    public Song(UUID artistId, String songTitle, String albumName, LocalDate releaseDate, String genre, String countryOfOrigin, String filePath, String image_path) {
        this(); // Call default constructor to generate UUID and set registration time
        this.artistId = artistId;
        this.songTitle = songTitle;
        this.albumName = albumName;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.countryOfOrigin = countryOfOrigin;
        this.filePath = filePath;
        this.playedTimes = 0;
        this.image_path = image_path;
    }

    // Getters and Setters


    public UUID getSongId() {
        return songId;
    }

    public void setSongId(UUID songId) {
        this.songId = songId;
    }

    public UUID getArtistId() {
        return artistId;
    }

    public void setArtistId(UUID artistId) {
        this.artistId = artistId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getPlayedTimes() {
        return playedTimes;
    }

    public void setPlayedTimes(int playedTimes) {
        this.playedTimes = playedTimes;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
