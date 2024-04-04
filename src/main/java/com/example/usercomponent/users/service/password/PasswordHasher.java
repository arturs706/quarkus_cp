package com.example.usercomponent.users.service.password;


public interface PasswordHasher {
    String hashPassword(String password);
    boolean validatePassword(String password, String hashedPassword);
}
