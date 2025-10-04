package dev.affoysal.backend.DTO;

import lombok.Data;

@Data
public class User {
    private Long id;
    private Long createdBy;
    private Long updatedBy;

    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String authorities;
    private String createdAt;
    private String updatedAt;

    private boolean verified;
}
