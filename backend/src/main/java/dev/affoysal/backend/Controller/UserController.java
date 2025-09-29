package dev.affoysal.backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.affoysal.backend.DTORequest.UserRequest;
import dev.affoysal.backend.Domain.Response;
import dev.affoysal.backend.Service.UserService;
import dev.affoysal.backend.Utility.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

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

    private URI getUri() {
        return URI.create("");
    }

}
