<%--
  Created by IntelliJ IDEA.
  User: Lemongrass
  Date: 28.08.2020
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="get">
    <input type="text" name="command" value="SEARCH_BOOK" size="40" hidden>
    Title
    <p><input type="text" name="title" size="40"></p>
    Authors
    <p><input type="text" name="author" size="40"></p>
    Genres
    <p><input type="text" name="genre" size="40"></p>
    Description
    <p><input type="text" name="description" size="40"></p>

    <input type="text" name="page" value="1" size="40" hidden>

    <button type="submit"/>
    Search
    </button>

</form>
</body>
</html>
