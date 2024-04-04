package com.example.songcomponent.song.models;

import java.time.LocalDate;
import java.util.UUID;

public class SongWithArtistName {
    public UUID songId;
    public String songTitle;
    public String albumName;
    public LocalDate releaseDate;
    public String genre;
    public String countryOfOrigin;
    public String filePath;
    public int playedTimes;
    public String image_path;
    public String artistName;

    public SongWithArtistName() {
        // Empty constructor required by Hibernate
    }

    public SongWithArtistName(Song song, String artistName) {
        this.songId = song.getSongId();
        this.songTitle = song.getSongTitle();
        this.albumName = song.getAlbumName();
        this.releaseDate = song.getReleaseDate();
        this.genre = song.getGenre();
        this.countryOfOrigin = song.getCountryOfOrigin();
        this.filePath = song.getFilePath();
        this.playedTimes = song.getPlayedTimes();
        this.image_path = song.getImage_path();
        this.artistName = artistName;
    }
}
