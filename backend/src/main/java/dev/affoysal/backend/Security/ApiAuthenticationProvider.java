package dev.affoysal.backend.Security;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import dev.affoysal.backend.Domain.ApiAuthentication;
import dev.affoysal.backend.Domain.UserPrincipal;
import dev.affoysal.backend.Exception.ApiException;
import dev.affoysal.backend.Service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var apiAuthentication = authenticationFunction.apply(authentication);
        var user = userService.getUserByEmail(apiAuthentication.getEmail());
        if (user != null) {
            var userCredential = userService.getUserCredentialById(user.getId());
            var userPrincipal = new UserPrincipal(user, userCredential);
            validAccount.accept(userPrincipal);
            if (encoder.matches(apiAuthentication.getPassword(), userCredential.getPassword())) {
                return ApiAuthentication.authenticated(user, userPrincipal.getAuthorities());
            } else
                throw new BadCredentialsException("Email and/or password incorrect. Please try again.");
        }
        throw new ApiException("Unable to authenticate");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiAuthentication.class.isAssignableFrom(authentication);
    }

    private final Function<Authentication, ApiAuthentication> authenticationFunction = ApiAuthentication.class::cast;

    private final Consumer<UserPrincipal> validAccount = userPrincipal -> {
        if (!userPrincipal.isEnabled()) {
            throw new DisabledException("Your account is currently disabled");
        }
    };

}
