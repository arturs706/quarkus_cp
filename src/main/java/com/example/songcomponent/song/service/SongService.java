package com.example.songcomponent.song.service;


import com.example.songcomponent.song.exceptions.SongNotFoundException;
import com.example.songcomponent.song.models.Song;
import com.example.songcomponent.song.models.SongWithArtistName;
import com.example.songcomponent.song.repository.SongRepository;
import com.example.usercomponent.users.repository.ArtistRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped

public class SongService {
    private SongRepository songRepository;
    private ArtistRepository artistRepository;
    private static final String UPLOAD_DIRECTORY = "/Users/artursradionovs/Desktop/distributedsystems/uwlify/src/main/resources/static/";

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public SongWithArtistName findById(UUID id) throws SongNotFoundException {

        Optional<SongWithArtistName> song = songRepository.findByIdWithArtistName(id);

        if (song.isEmpty()) {
            throw new SongNotFoundException();
        }
        return song.get();
    }

    public List<Song> getAll() throws SongNotFoundException {
        List<Song> song = songRepository.findAllSongs();
        if (song.isEmpty()) {
            throw new SongNotFoundException();
        }
        return song;
    }
    public List<SongWithArtistName> getAllWithArtist() throws SongNotFoundException {
        List<SongWithArtistName> song = songRepository.findAllSongsWithArtistName();
        if (song.isEmpty()) {
            throw new SongNotFoundException();
        }
        return song;
    }


    public Response saveSong(InputStream fileInputStream) {
        String UPLOAD_DIRECTORY = "/Users/artursradionovs/Desktop/distributedsystems/uwlify/src/main/resources/static/";
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
            String fileName = "audio_" + System.currentTimeMillis() + ".mp3";
            File uploadedFile = new File(UPLOAD_DIRECTORY + fileName);
            try (FileOutputStream outputStream = new FileOutputStream(uploadedFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
            URI fileUri = UriBuilder.fromUri("http://10.0.2.2:8080")
                    .path("static")
                    .path(fileName)
                    .build();
            return Response.ok()
                    .entity("{\"status\": \"success\", \"message\": \"File uploaded successfully\", \"url\": \"" + fileUri.toString() + "\"}")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"status\": \"failed\", \"message\": \"Failed to upload file\"}")
                    .build();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Response saveSongToDb(Song song) {
        songRepository.save(song);
        return Response.ok()
                .entity("{\"status\": \"success\", \"message\": \"Song uploaded successfully\"}")
                .build();
    }

    public Response updatePlayedTimes(UUID songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new IllegalArgumentException("Song not found"));
        songRepository.updatePlayedTimes(song);
        return Response.ok()
                .entity("{\"status\": \"success\", \"message\": \"Played times updated successfully\"}")
                .build();
    }






}