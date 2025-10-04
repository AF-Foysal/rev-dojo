package dev.affoysal.backend.DTORequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPasswordRequest {

    @NotEmpty(message = "Email cannot be empty or null.")
    private String email;
    @NotEmpty(message = "New password cannot be empty or null.")
    private String newPassword;
    @NotEmpty(message = "Confirm password cannot be empty or null.")
    private String confirmNewPassword;

}
