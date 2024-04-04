package com.example.usercomponent.users.repository;


import com.example.usercomponent.users.models.Artist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ArtistRepository implements PanacheRepository<Artist> {
    private final EntityManager entityManager;
    public ArtistRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public List<Artist> findAllArtists() {
        return listAll();
    }

    public Optional<Artist> findArtistById(UUID artistId) {
        return find("artistId", artistId).firstResultOptional();
    }
    public List<Artist> findByName(String name) {
        return find("name", name).list();
    }

    public Optional<Artist> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
    @Transactional
    public void saveArtist(Artist artist) {
        entityManager.merge(artist);
    }

    @Transactional
    public void updateArtistValidation(UUID artistId, boolean validated) {
        findArtistById(artistId).ifPresent(artist -> {
            artist.setValidated(validated);
            entityManager.merge(artist);
        });
    }

    @Transactional
    public UUID findArtistIdByUserId(UUID userId) {

        return (UUID) entityManager.createQuery(
                        "SELECT a.artistId FROM Artist a WHERE a.user_id = :userId")
                .setParameter("userId", userId)
                .getSingleResult();
    }
}