package com.example.usercomponent.users.repository;


import com.example.usercomponent.users.models.Artist;
import com.example.usercomponent.users.models.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;



@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    private final EntityManager entityManager;
    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public List<User> findAllUsers() {
        return listAll();
    }

    public Optional<User> findById(UUID userId) {
        return find("userId", userId).firstResultOptional();
    }
    public List<User> findByName(String name) {
        return find("name", name).list();
    }

    public Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
    public Optional<User> findByMobPhone(String mobile) {
        return find("mobile", mobile).firstResultOptional();
    }

    public List<User> findByAccessLevel(String accessLevel) {
        return find("accessLevel", accessLevel).list();
    }
    @Transactional
    public void save(User user) {
        entityManager.merge(user);
    }
    @Transactional
    public void delete(UUID userId) {
        delete("userId", userId);
    }

    @Transactional
    public void updateUserType(UUID userId, String userType) {
        findById(userId).ifPresent(user -> {
            user.setUserType(userType);
            entityManager.merge(user);
        });
    }
}