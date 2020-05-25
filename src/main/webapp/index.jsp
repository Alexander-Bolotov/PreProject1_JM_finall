<%@ page import="src.main.service.UserService" %>
<%@ page import="javax.jws.soap.SOAPBinding" %><%
    UserService userService = UserService.getInstance();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Проект 1 предпроектной подготовки</h2>
<hr />
<p> Добавить User-a в БД
<%--    <a href="registration.html">Добавить пользователя</a>--%>
    <form action="${pageContext.request.contextPath}/registration" method="POST">
        Name: <input type="text" name="name"/>
<p>
    Password: <input type="password" name="password"/>
<p>
    <input type="submit" value="Submit">
    </form>
<hr />
<form action="${pageContext.request.contextPath}/delete" method="POST">
    Удалить пользователя по имени
    Name: <input type="text" name="name"/>
        <p>
        <input type="submit" value="Submit">
</form>
<hr />
<p> Пользователи <%= userService.getAllUsers().toString() %></p>
</body>
</html>