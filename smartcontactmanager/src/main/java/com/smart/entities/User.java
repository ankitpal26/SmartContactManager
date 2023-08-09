package com.smart.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;
    private String name;
    private String email;
    private String password;
    private String role;
    private  boolean enabled;
    private String imageUrl;
    private String about;


}
