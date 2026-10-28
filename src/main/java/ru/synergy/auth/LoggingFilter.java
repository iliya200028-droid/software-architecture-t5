package ru.synergy.auth;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.Instant;

@WebFilter("/*")
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        String message = String.format(
                "REQUEST | time=%s | method=%s | URL=%s | IP=%s",
                Instant.now(),
                httpRequest.getMethod(),
                httpRequest.getRequestURL(),
                httpRequest.getRemoteAddr()
        );

        request.getServletContext().log(message);

        // Передаём запрос следующему фильтру или сервлету.
        chain.doFilter(request, response);
    }
}