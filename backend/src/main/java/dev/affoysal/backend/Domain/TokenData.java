package dev.affoysal.backend.Domain;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import dev.affoysal.backend.DTO.User;
import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TokenData {
    private User user;
    private Claims claims;
    private boolean valid;
    private List<GrantedAuthority> authorities;
}
