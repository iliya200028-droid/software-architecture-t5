<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Профиль пользователя</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #eef2f7;
            color: #172338;
            padding: 40px 20px;
        }

        main {
            max-width: 560px;
            margin: 30px auto;
            padding: 32px;
            background: white;
            border-radius: 16px;
        }

        p {
            line-height: 1.6;
        }

        button {
            padding: 12px 20px;
            border: none;
            border-radius: 7px;
            background: #b42318;
            color: white;
            font-size: 18px;
            cursor: pointer;
        }

        a {
            color: #2455b8;
        }
    </style>
</head>
<body>
<main>
    <h1>Профиль пользователя</h1>

    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <p>
                Имя пользователя:
                <strong><c:out value="${sessionScope.user}"/></strong>
            </p>

            <c:if test="${not empty sessionScope.loginTime}">
                <p>
                    Время входа (UTC):
                    <c:out value="${sessionScope.loginTime}"/>
                </p>
            </c:if>

            <p>Вы успешно авторизованы.</p>

            <c:url var="logoutUrl" value="/logout"/>

            <form method="post" action="${logoutUrl}">
                <button type="submit">Выйти / Logout</button>
            </form>

            <c:url var="welcomeUrl" value="/welcome"/>

            <p>
                <a href="${welcomeUrl}">К приветствию</a>
            </p>
        </c:when>

        <c:otherwise>
            <p>Для просмотра профиля необходимо войти.</p>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>