package com.example.songcomponent.song.repository;

import com.example.songcomponent.song.models.Song;
import com.example.songcomponent.song.models.SongWithArtistName;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SongRepository implements PanacheRepository<Song> {

    private final EntityManager entityManager;
    public SongRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public List<Song> findAllSongs() {
        return listAll();
    }

    public Optional<Song> findById(UUID songId) {
        return find("songId", songId).firstResultOptional();
    }
    @Transactional
    public void save(Song song) {
        entityManager.persist(song);
    }
    public void updateSong(UUID songId, Song updatedSong) {
        Song song = findById(songId).orElseThrow(() -> new IllegalArgumentException("Song not found"));
        song.setSongTitle(updatedSong.getSongTitle());
        song.setAlbumName(updatedSong.getAlbumName());
        song.setReleaseDate(updatedSong.getReleaseDate());
        song.setGenre(updatedSong.getGenre());
        song.setCountryOfOrigin(updatedSong.getCountryOfOrigin());
        song.setFilePath(updatedSong.getFilePath());
        persist(song);
    }

    public void deleteSong(UUID songId) {
        Song song = findById(songId).orElseThrow(() -> new IllegalArgumentException("Song not found"));
        delete(song);
    }

    public List<SongWithArtistName> findAllSongsWithArtistName() {
        String queryStr = "SELECT s, a.artistName FROM Song s JOIN Artist a ON s.artistId = a.artistId";
        return entityManager.createQuery(queryStr, SongWithArtistName.class).getResultList();
    }


    public Optional<SongWithArtistName> findByIdWithArtistName(UUID songId) {
        String queryStr = "SELECT s, a.artistName FROM Song s JOIN Artist a ON s.artistId = a.artistId WHERE s.songId = :songId";
        return entityManager.createQuery(queryStr, SongWithArtistName.class)
                .setParameter("songId", songId)
                .getResultStream()
                .findFirst();
    }
    @Transactional
    public void updatePlayedTimes(Song song) {
        song.setPlayedTimes(song.getPlayedTimes() + 1);
        update("playedTimes = ?1 where songId = ?2", song.getPlayedTimes(), song.getSongId());
    }


}
