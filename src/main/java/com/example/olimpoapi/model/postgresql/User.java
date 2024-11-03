package com.example.olimpoapi.model.postgresql;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Column(name = "password")
    private String password;

    @NotNull(message = "Name cannot be null")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Surname cannot be null")
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "CPF cannot be null")
    @Size(min = 11, max = 11, message = "CPF must be exactly 11 characters")
    @Column(name = "cpf")
    private String cpf;

    @NotNull(message = "Profile image cannot be null")
    @Column(name = "profile_image")
    private String profileImage;

    @NotNull(message = "Gender ID cannot be null")
    @Column(name = "gender_id")
    private Integer genderId;

    public User() {
    }

    public User(Integer id, String email, String password, String name, String surname, String cpf, String profileImage, Integer genderId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cpf = cpf;
        this.profileImage = profileImage;
        this.genderId = genderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }
}