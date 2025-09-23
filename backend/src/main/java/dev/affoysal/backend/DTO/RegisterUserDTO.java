package dev.affoysal.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {

    private String firstName;
    private String lastName;
    private String email;
}
