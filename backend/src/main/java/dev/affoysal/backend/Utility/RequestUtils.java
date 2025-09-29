package dev.affoysal.backend.Utility;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import dev.affoysal.backend.Domain.Response;
import jakarta.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class RequestUtils {
    public static Response getResponse(HttpServletRequest request, Map<?, ?> data, String message, HttpStatus status) {
        return new Response(Instant.now().toString(), status.value(), request.getRequestURI(),
                HttpStatus.valueOf(status.value()), message, EMPTY, data);

    }
}
