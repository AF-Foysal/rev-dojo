package dev.affoysal.backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.affoysal.backend.DTORequest.EmailRequest;
import dev.affoysal.backend.DTORequest.ResetPasswordRequest;
import dev.affoysal.backend.DTORequest.UserRequest;
import dev.affoysal.backend.Domain.Response;
import dev.affoysal.backend.Service.UserService;
import dev.affoysal.backend.Utility.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static java.util.Collections.emptyMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserRequest user, HttpServletRequest request) {
        userService.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        return ResponseEntity.created(getUri()).body(
                RequestUtils.getResponse(request, emptyMap(),
                        "Account created. Check your email to verify your account", HttpStatus.CREATED));
    }

    @GetMapping("/verify/account")
    public ResponseEntity<Response> verifyUser(@RequestParam("token") String token, HttpServletRequest request) {
        userService.verifyAccountToken(token);
        return ResponseEntity.ok()
                .body(RequestUtils.getResponse(request, emptyMap(), "Account verified.", HttpStatus.OK));
    }

    // START - Reset Password when user is NOT logged in

    @PostMapping("/reset-password")
    public ResponseEntity<Response> sendResetPasswordEmail(@RequestBody @Valid EmailRequest emailRequest,
            HttpServletRequest request) {
        userService.resetPassword(emailRequest.getEmail());
        return ResponseEntity.ok().body(
                RequestUtils.getResponse(request, emptyMap(),
                        "Email sent to reset password", HttpStatus.OK));
    }

    @GetMapping("/verify/reset-password")
    public ResponseEntity<Response> verifyResetPassword(@RequestParam("token") String token,
            HttpServletRequest request) {
        var user = userService.verifyPasswordToken(token);
        return ResponseEntity.ok()
                .body(RequestUtils.getResponse(request, Map.of("user", user), "Enter new password.", HttpStatus.OK));
    }

    @PostMapping("/reset-password/reset")
    public ResponseEntity<Response> resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest,
            HttpServletRequest request) {
        userService.updatePassword(resetPasswordRequest.getEmail(), resetPasswordRequest.getNewPassword(),
                resetPasswordRequest.getConfirmNewPassword());
        return ResponseEntity.ok().body(
                RequestUtils.getResponse(request, emptyMap(),
                        "Password reset successfully", HttpStatus.OK));
    }

    // END - Reset Password when user is NOT logged in

    private URI getUri() {
        return URI.create("");
    }

}
