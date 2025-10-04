package dev.affoysal.backend.Security;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.affoysal.backend.Constant.Constants;
import dev.affoysal.backend.DTO.User;
import dev.affoysal.backend.DTORequest.LoginRequest;
import dev.affoysal.backend.Domain.ApiAuthentication;
import dev.affoysal.backend.Domain.Response;
import dev.affoysal.backend.Enumeration.TokenType;
import dev.affoysal.backend.Service.JwtService;
import dev.affoysal.backend.Utility.RequestUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtService jwtService;

    public ApiAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(new AntPathRequestMatcher(Constants.LOGIN_PATH, HttpMethod.POST.name()), authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        try {
            var user = new ObjectMapper().configure(Feature.AUTO_CLOSE_SOURCE, true).readValue(request.getInputStream(),
                    LoginRequest.class);
            var authentication = ApiAuthentication.unauthenticated(user.getEmail(), user.getPassword());
            return getAuthenticationManager().authenticate(authentication);
        } catch (Exception e) {
            log.error(e.getMessage());
            RequestUtils.handleErrorResponse(request, response, e);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {
        var user = (User) authentication.getPrincipal();
        var httpResponse = sendResponse(request, response, user);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        var output = response.getOutputStream();
        var mapper = new ObjectMapper();
        mapper.writeValue(output, httpResponse);
        output.flush();
    }

    private Response sendResponse(HttpServletRequest request, HttpServletResponse response, User user) {
        jwtService.addCookie(response, user, TokenType.ACCESS);
        jwtService.addCookie(response, user, TokenType.REFRESH);
        return RequestUtils.getResponse(request, Map.of("user", user), "Login Success", HttpStatus.OK);
    }

}
