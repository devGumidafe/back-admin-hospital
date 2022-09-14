package org.gumidev.springweb.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "is required")
    @Size(min = 3, max = 20, message = "size must be between 3 and 20")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "is required")
    @Size(min = 3, max = 20, message = "size must be between 3 and 20")
    private String lastName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "is required")
    @Email(message = "is not a valid email")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "is required")
    @Size(min = 6, message = "must have at least 6 characters")
    private String password;

    private String image;

    @Column(nullable = false)
    private String role = "USER";

    @Column(name = "google_created")
    private boolean googleCreated = false;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isGoogleCreated() {
        return googleCreated;
    }

    public void setGoogleCreated(boolean googleCreated) {
        this.googleCreated = googleCreated;
    }
}
