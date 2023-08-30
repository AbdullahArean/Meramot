package com.example.meramot_back.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Data
@Table(name = "userr")
public class User {
    @Id
    @Column(name = "uid", nullable = false, unique = true)
    private UUID uid;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "role")
    private String role;
    @Column(name = "password")
    private String password;
    @Column(name = "profile_pic")
    private String profile_pic;


    public User(UUID uid, String email, String name, String role, String password, String profile_pic) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.role = role;
        this.password = password;
        this.profile_pic = profile_pic;
    }

    public User() {

    }


    public void setId(UUID id) {
        this.uid = id;
    }

    public UUID getId() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String toString() {
        return "User{" +
                "id=" + uid +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
