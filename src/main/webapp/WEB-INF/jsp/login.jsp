<%--
  Created by IntelliJ IDEA.
  User: Lemongrass
  Date: 3/2/2021
  Time: 12:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="auth" method="post">
    <div class="container">
        <label for="login"><b>Username</b></label>
        <input id="login" type="text" placeholder="Enter Username" name="login" required>

        <label for="password"><b>Password</b></label>
        <input id="password" type="password" placeholder="Enter Password" name="password" required>

        <button type="submit">Login</button>
    </div>
</form>
</body>
</html>
