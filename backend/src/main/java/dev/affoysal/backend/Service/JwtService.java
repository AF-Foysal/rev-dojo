package dev.affoysal.backend.Service;

import java.util.Optional;
import java.util.function.Function;

import dev.affoysal.backend.DTO.User;
import dev.affoysal.backend.Domain.Token;
import dev.affoysal.backend.Domain.TokenData;
import dev.affoysal.backend.Enumeration.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtService {
    String createToken(User user, Function<Token, String> tokenFunction);

    Optional<String> extractToken(HttpServletRequest request, String cookieName);

    void addCookie(HttpServletResponse response, User user, TokenType type);

    <T> T getTokenData(String token, Function<TokenData, T> tokenFunction);

    void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName);
}
