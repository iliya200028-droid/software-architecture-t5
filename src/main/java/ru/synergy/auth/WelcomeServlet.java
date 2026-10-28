package ru.synergy.auth;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        if (!Html.requireLogin(request, response)) {
            return;
        }

        String username = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        String greeting = username == null
                ? "Добро пожаловать! Cookie с именем отсутствует."
                : "Добро пожаловать, " + Html.escape(username) + "!";

        String base = Html.escape(request.getContextPath());

        Html.page(
                response,
                "Приветствие",
                "<p>" + greeting + "</p>"
                        + "<form method='get' action='"
                        + base + "/profile.jsp'>"
                        + "<button type='submit'>Мой профиль</button>"
                        + "</form>"
                        + "<p><a href='" + base + "/userinfo'>"
                        + "Информация о пользователе"
                        + "</a></p>"
        );
    }
}