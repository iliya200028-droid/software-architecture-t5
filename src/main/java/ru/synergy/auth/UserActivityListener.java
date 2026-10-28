package ru.synergy.auth;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.time.Instant;

@WebListener
public class UserActivityListener
        implements HttpSessionListener, HttpSessionAttributeListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        writeLog(
                event.getSession(),
                "SESSION_CREATED",
                "anonymous"
        );
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
            writeLog(
                    event.getSession(),
                    "LOGIN",
                    String.valueOf(event.getValue())
            );
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
            Object currentUser = event.getSession().getAttribute("user");

            writeLog(
                    event.getSession(),
                    "USER_CHANGED",
                    String.valueOf(currentUser)
            );
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
            writeLog(
                    event.getSession(),
                    "USER_REMOVED",
                    String.valueOf(event.getValue())
            );
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        Object username = session.getAttribute("user");

        writeLog(
                session,
                "SESSION_DESTROYED",
                username == null ? "anonymous" : username.toString()
        );
    }

    private void writeLog(
            HttpSession session,
            String action,
            String username
    ) {
        session.getServletContext().log(
                String.format(
                        "ACTIVITY | time=%s | action=%s | user=%s",
                        Instant.now(),
                        action,
                        username
                )
        );
    }
}