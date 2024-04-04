package com.example.usercomponent.users.service;

import com.example.usercomponent.users.exceptions.UserConflictException;
import com.example.usercomponent.users.exceptions.UserNotFoundException;
import com.example.usercomponent.users.models.Artist;
import com.example.usercomponent.users.models.User;
import com.example.usercomponent.users.repository.ArtistRepository;
import com.example.usercomponent.users.repository.UserRepository;
import com.example.usercomponent.users.service.password.Hash;
import com.example.usercomponent.users.service.password.PasswordHasher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    private UserRepository userRepository;
    private ArtistRepository artistRepository;

    public UserService(UserRepository userRepository, ArtistRepository artistRepository) {
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
    }


    public User findById(UUID id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<User> findAll() throws UserNotFoundException {
        List<User> users = userRepository.findAllUsers();

        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return users;
    }


    public List<Artist> findAllArtists() throws UserNotFoundException {
        List<Artist> artists = artistRepository.findAllArtists();
        if (artists.isEmpty()) {
            throw new UserNotFoundException("No artists found");
        }
        return artists;
    }

    @Transactional
    public void registerUser(User user) {
        Optional<User> existingUserEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> existingUserMobile = userRepository.findByMobPhone(user.getMobile());

        if (existingUserEmail.isPresent()) {
            throw new UserConflictException("Email address already exists: " + user.getEmail());
        } else if (existingUserMobile.isPresent()) {
            throw new UserConflictException("Mobile already exists: " + user.getMobile());
        }

        PasswordHasher hasher = new Hash();
        user.setPassword(hasher.hashPassword(user.getPassword()));
        userRepository.save(user);

        if (user.getUserType().equals("Artist")) {
            User savedUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found after saving."));
            Artist artist = new Artist(savedUser.getFullname(), false, savedUser.getUserId());
            artistRepository.saveArtist(artist);
        }
    }

    public Optional<User> loginUser(String email, String passwd) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            User foundUser = user.get();
            PasswordHasher hasher = new Hash();
            boolean isValid = hasher.validatePassword(passwd, foundUser.getPassword());
            if (isValid) {
                return user;
            }
        }
        throw new UserNotFoundException("User not found or invalid credentials");
    }

    public void deleteUser(UUID id) {
        userRepository.delete(id);
    }

    public void updateUserAndArtist(UUID userId, boolean validated) {
        artistRepository.saveArtist(new Artist(userRepository.findById(userId).get().getFullname(), validated, userId));
        userRepository.updateUserType(userId, "Artist");
    }

    public UUID getArtistId(UUID userId) {
        System.out.println("User ID: " + userId);
        UUID artist = artistRepository.findArtistIdByUserId(userId);
        System.out.println("Artist ID: " + artist);
        return artist;
    }
}
