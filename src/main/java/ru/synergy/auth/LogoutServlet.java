package ru.synergy.auth;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            Object username = session.getAttribute("user");

            getServletContext().log(
                    "ACTIVITY | time=" + Instant.now()
                            + " | action=LOGOUT"
                            + " | user=" + username
            );

            // Уничтожение сессии также вызовет наш слушатель.
            session.invalidate();
        }

        String cookiePath = request.getContextPath().isEmpty()
                ? "/"
                : request.getContextPath();

        for (String name : new String[]{"username", "JSESSIONID"}) {
            Cookie cookie = new Cookie(name, "");
            cookie.setMaxAge(0);
            cookie.setPath(cookiePath);
            cookie.setHttpOnly(true);
            cookie.setSecure(request.isSecure());
            response.addCookie(cookie);
        }

        response.setHeader("Cache-Control", "no-store");
        response.sendRedirect(request.getContextPath() + "/login");
    }
}