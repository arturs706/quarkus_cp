package com.example.songcomponent.song.controller;

import com.example.songcomponent.song.exceptions.SongNotFoundException;
import com.example.songcomponent.song.models.Song;
import com.example.songcomponent.song.models.SongWithArtistName;
import com.example.songcomponent.song.repository.SongRepository;
import com.example.songcomponent.song.service.SongService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.UUID;
import org.glassfish.jersey.media.multipart.*;
import jakarta.annotation.security.PermitAll;


@Path("/api/v1/songs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SongController {

    @Inject
    SongService songService;

    @GET
    @Path("/{songId}")
    public Response getSingleSong(@PathParam("songId") UUID songId) throws SongNotFoundException {
        SongWithArtistName returnedSong = songService.findById(songId);
        return Response.ok(returnedSong).build();
    }



    @GET
    @PermitAll
    @Path("/")
    public Response getAllSongsWithArtist() throws SongNotFoundException {
        List<SongWithArtistName> returnedSong = songService.getAllWithArtist();
        return Response.ok(returnedSong).build();
    }


    @POST
    @Path("/savesong")
    @PermitAll
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savesong(@FormDataParam("file") InputStream fileInputStream) {
        return songService.saveSong(fileInputStream);
    }

    @POST
    @Path("/upload")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(Song song) {
        return songService.saveSongToDb(song);
    }

    @PUT
    @Path("/update/{songId}")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSong(@PathParam("songId") UUID songId) {
        return songService.updatePlayedTimes(songId);
    }



}

