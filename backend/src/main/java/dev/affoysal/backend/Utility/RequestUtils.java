package dev.affoysal.backend.Utility;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.affoysal.backend.Domain.Response;
import dev.affoysal.backend.Exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class RequestUtils {
    public static Response getResponse(HttpServletRequest request, Map<?, ?> data, String message, HttpStatus status) {
        return new Response(Instant.now().toString(), status.value(), request.getRequestURI(),
                HttpStatus.valueOf(status.value()), message, EMPTY, data);
    }

    public static void handleErrorResponse(HttpServletRequest request, HttpServletResponse response,
            Exception exception) {
        if (exception instanceof AccessDeniedException) {
            var apiResponse = getErrorResponse(request, response, exception, HttpStatus.FORBIDDEN);
            writeResponse.accept(response, apiResponse);
        }
    }

    public static Response getErrorResponse(HttpServletRequest request, HttpServletResponse response,
            Exception exception, HttpStatus status) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        return new Response(Instant.now().toString(), status.value(), request.getRequestURI(),
                HttpStatus.valueOf(status.value()), errorReason.apply(exception, status),
                ExceptionUtils.getRootCauseMessage(exception), emptyMap());
    }

    private static final BiConsumer<HttpServletResponse, Response> writeResponse = (httpServletResponse, response) -> {
        try {
            var outputStream = httpServletResponse.getOutputStream();
            new ObjectMapper().writeValue(outputStream, response);
            outputStream.flush();
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    };

    private static final BiFunction<Exception, HttpStatus, String> errorReason = (exception, httpStatus) -> {
        if (httpStatus.isSameCodeAs(HttpStatus.FORBIDDEN)) {
            return "You do not have enough permission.";
        }
        if (httpStatus.isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
            return "You are not logged in.";
        }
        if (exception instanceof DisabledException
                || exception instanceof BadCredentialsException
                || exception instanceof ApiException) {
            return exception.getMessage();
        }
        if (httpStatus.is5xxServerError()) {
            return "An internal server error has occured.";
        } else {
            return "An error occured. Please try again.";
        }
    };

}
