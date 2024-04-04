package com.example.usercomponent.users.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id", length = 40, nullable = false)
    private UUID userId;

    @Column(name = "fullname", length = 150, nullable = false)
    private String fullname;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;

    @Column(name = "mobile", length = 255, unique = true, nullable = false)
    private String mobile;

    @Column(name = "user_type", length = 20, nullable = false)
    private String userType;

    @Column(name = "reg_time", nullable = false)
    private LocalDateTime regTime;

    @Column(name = "auth_method", length = 20, nullable = false)
    private String auth_method;

    @Column(name = "facebook_id", length = 255)
    private String facebook_id;

    @Column(name = "google_id", length = 255)
    private String google_id;

    @Column(name = "gender", length = 10, nullable = false)
    private String gender;

    @Column(name = "membership", length = 10, nullable = false)
    private String membership;

    // Add the dob field with appropriate annotations
    @Column(name = "dob")
    private String dob;


    // Constructors

    public User() {
        this.userId = UUID.randomUUID();
        this.regTime = LocalDateTime.now();
    }

    public User(String fullname, String password, String email, String mobile,
                String userType, String auth_method, String facebook_id, String google_id, String gender, String membership) {
        this(); // Call default constructor to generate UUID and set registration time
        this.fullname = fullname;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.userType = userType;
        this.auth_method = auth_method;
        this.facebook_id = facebook_id;
        this.google_id = google_id;
        this.gender = gender;
        this.membership = membership;
    }

    // Getters and Setters

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }

    public String getAuth_method() {
        return auth_method;
    }

    public void setAuth_method(String auth_method) {
        this.auth_method = auth_method;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getGender() {
        return gender;
    }

    public void  setGender(String gender) {
        this.gender = gender;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
