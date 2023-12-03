package edu.corvinus.beadando_csoport_6.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;


    @NotEmpty(message = "Username is required")
    @Size(min = 5, message = "Username must be between 7 and 15 characters")
    @Column(name = "NAME")
    private String name;

    @NotEmpty(message = "Password is required")
    @Size(min = 5, message = "Password must be between 6 and 10 characters")
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "VOTE")
    @Basic
    private Boolean vote;

    @Column(name = "SELECTED_FLAVOR")
    private String selectedFlavor;


    public User(){
        // Default constructor required by JPA
    }

    public User(String name, String password, String role, Boolean vote) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.vote = vote;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Boolean getVote() {
        return vote;
    }

    public void setVote(Boolean vote) {
        this.vote = vote;
    }


    public String getSelectedFlavor() {
        return selectedFlavor;
    }

    public void setSelectedFlavor(String selectedFlavor) {
        this.selectedFlavor = selectedFlavor;
    }
}

