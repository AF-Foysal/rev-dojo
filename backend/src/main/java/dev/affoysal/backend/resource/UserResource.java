package dev.affoysal.backend.resource;

import dev.affoysal.backend.domain.Response;
import dev.affoysal.backend.dto.User;
import dev.affoysal.backend.dtorequset.*;
import dev.affoysal.backend.handler.ApiLogoutHandler;
import dev.affoysal.backend.service.JwtService;
import dev.affoysal.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static dev.affoysal.backend.constant.Constants.FILE_STORAGE;
import static dev.affoysal.backend.enumeration.TokenType.ACCESS;
import static dev.affoysal.backend.enumeration.TokenType.REFRESH;
import static dev.affoysal.backend.utils.RequestUtils.getResponse;
import static java.net.URI.create;
import static java.util.Collections.emptyMap;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/user"})
public class UserResource {
    private final UserService userService;
    private final JwtService jwtService;
    private final ApiLogoutHandler apiLogoutHandler;

    @PostMapping("/register")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserRequest user, HttpServletRequest request) {
        userService.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        return ResponseEntity.created(create("")).body(getResponse(request, emptyMap(), "Account created. Check your email to enable your account.", CREATED));
    }

    @GetMapping("/verify/account")
    public ResponseEntity<Response> verifyAccount(@RequestParam("key") String key, HttpServletRequest request) {
        userService.verifyAccountKey(key);
        return ResponseEntity.ok(getResponse(request, emptyMap(), "Account verified.", OK));
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('user:read') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> profile(@AuthenticationPrincipal User userPrincipal, HttpServletRequest request) {
        var user = userService.getUserByUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(getResponse(request, of("user", user), "Profile Retrieved", OK));

    }

    @PatchMapping("/update")
    @PreAuthorize("hasAnyAuthority('user:update') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> updateProfile(@AuthenticationPrincipal User userPrincipal, @RequestBody UserRequest userRequest, HttpServletRequest request) {
        var user = userService.updateUser(userPrincipal.getUserId(), userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPhone(), userRequest.getBio());
        return ResponseEntity.ok(getResponse(request, of("user", user), "User updated successfully", OK));

    }

    @PatchMapping("/updaterole")
    @PreAuthorize("hasAnyAuthority('user:update') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> updateRole(@AuthenticationPrincipal User userPrincipal, @RequestBody RoleRequest roleRequest, HttpServletRequest request) {
        userService.updateRole(userPrincipal.getUserId(), roleRequest.getRole());
        return ResponseEntity.ok(getResponse(request, emptyMap(), "Role updated successfully", OK));

    }

    @PatchMapping("/toggleaccountexpired")
    @PreAuthorize("hasAnyAuthority('user:update') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> toggleAccountExpired(@AuthenticationPrincipal User user, HttpServletRequest request) {
        userService.toggleAccountExpired(user.getUserId());
        return ResponseEntity.ok(getResponse(request, emptyMap(), "Account updated successfully", OK));

    }

    @PatchMapping("/toggleaccountlocked")
    @PreAuthorize("hasAnyAuthority('user:update') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> toggleAccountLocked(@AuthenticationPrincipal User user, HttpServletRequest request) {
        userService.toggleAccountLocked(user.getUserId());
        return ResponseEntity.ok(getResponse(request, emptyMap(), "Account updated successfully", OK));

    }

    @PatchMapping("/toggleaccountenabled")
    @PreAuthorize("hasAnyAuthority('user:update') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> toggleAccountEnabled(@AuthenticationPrincipal User user, HttpServletRequest request) {
        userService.toggleAccountEnabled(user.getUserId());
        return ResponseEntity.ok(getResponse(request, emptyMap(), "Account updated successfully", OK));

    }

    @PatchMapping("/mfa/setup")
    @PreAuthorize("hasAnyAuthority('user:update') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> setUpMfa(@AuthenticationPrincipal User userPrincipal, HttpServletRequest request) {
        var user = userService.setUpMfa(userPrincipal.getId());
        return ResponseEntity.ok(getResponse(request, of("user", user), "MFA set up successfully", OK));
    }

    @PatchMapping("/mfa/cancel")
    @PreAuthorize("hasAnyAuthority('user:update') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> cancelMfa(@AuthenticationPrincipal User userPrincipal, HttpServletRequest request) {
        var user = userService.cancelMfa(userPrincipal.getId());
        return ResponseEntity.ok(getResponse(request, of("user", user), "MFA canceled successfully", OK));
    }

    @PostMapping("/verify/qrcode")
    public ResponseEntity<Response> verifyQrcode(@RequestBody QrCodeRequest qrCodeRequest, HttpServletRequest request, HttpServletResponse response) {
        var user = userService.verifyQrCode(qrCodeRequest.getUserId(), qrCodeRequest.getQrCode());
        jwtService.addCookie(response, user, ACCESS);
        jwtService.addCookie(response, user, REFRESH);
        return ResponseEntity.ok(getResponse(request, of("user", user), "QR code verified", OK));
    }

    // START - Update Password when user IS logged in

    @PatchMapping("/updatepassword")
    @PreAuthorize("hasAnyAuthority('user:update') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> updatePassword(@AuthenticationPrincipal User user, @RequestBody UpdatePasswordRequest passwordRequest, HttpServletRequest request) {
        userService.updatePassword(user.getUserId(), passwordRequest.getPassword(), passwordRequest.getNewPassword(), passwordRequest.getConfirmNewPassword());
        return ResponseEntity.ok(getResponse(request, emptyMap(), "Password updated successfully", OK));

    }

    // END - Update Password when user IS logged in

    // START - Reset Password when user is NOT logged in

    @PostMapping("/resetpassword")
    public ResponseEntity<Response> sendResetPasswordEmail(@RequestBody @Valid EmailRequest emailRequest,
                                                           HttpServletRequest request) {
        userService.resetPassword(emailRequest.getEmail());
        return ResponseEntity.ok(
                getResponse(request, emptyMap(),
                        "Email sent to reset password", HttpStatus.OK));
    }

    @GetMapping("/verify/resetpassword")
    public ResponseEntity<Response> verifyResetPassword(@RequestParam("key") String key,
                                                        HttpServletRequest request) {
        var user = userService.verifyPasswordKey(key);
        return ResponseEntity.ok(getResponse(request, Map.of("user", user), "Enter new password.", HttpStatus.OK));
    }

    @PostMapping("/resetpassword/reset")
    public ResponseEntity<Response> resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest,
                                                  HttpServletRequest request) {
        userService.updatePassword(resetPasswordRequest.getUserId(), resetPasswordRequest.getNewPassword(),
                resetPasswordRequest.getConfirmNewPassword());
        return ResponseEntity.ok(
                getResponse(request, emptyMap(),
                        "Password reset successfully", HttpStatus.OK));
    }

    // END - Reset Password when user is NOT logged in

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('user:read') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> getUsers(@AuthenticationPrincipal User user, HttpServletRequest request){
        return ResponseEntity.ok(getResponse(request, of("users", userService.getUsers()), "Users Retrieved", OK));
    }

    @PatchMapping("/photo")
    @PreAuthorize("hasAnyAuthority('user:update') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Response> uploadPhoto(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        var imageUrl = userService.uploadPhoto(user.getUserId(), file);
        return ResponseEntity.ok(getResponse(request, of("imageUrl", imageUrl), "Photo uploaded successfully", OK));
    }

    @GetMapping(value = "/image/{file_name}", produces = {IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE})
    public byte[] getPhoto(@PathVariable("file_name") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(FILE_STORAGE + fileName));
    }

    @PostMapping("/logout")
    public ResponseEntity<Response> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        apiLogoutHandler.logout(request, response, authentication);
        return ResponseEntity.ok(getResponse(request, emptyMap(), "Logged out successfully", OK));
    }

}