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
<form method="post">

    Title
    <p><input type="text" size="40"></p>
    Authors
    <p><input type="text" size="40"></p>
    Genres
    <p><input type="text" size="40"></p>
    Description
    <p><input type="text" size="40"></p>

    <button type="submit" formaction="lib-app?command=SEARCH_BOOK" />
    Search
    </button>

</form>
</body>
</html>
