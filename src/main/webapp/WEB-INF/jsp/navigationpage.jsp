<%--
  Created by IntelliJ IDEA.
  User: Lemongrass
  Date: 24.08.2020
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-sm bg-light navbar-light">
    <a class="navbar-brand" href="lib-app?command=GET_BOOK_LIST&page=1">Library Management</a>
<%--    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">--%>
<%--        <span class="navbar-toggler-icon"></span>--%>
<%--    </button>--%>
    <div id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a href="lib-app?command=GET_BOOK_LIST&page=1">Main page</a>
            </li>
            <li class="nav-item">
                <a href="lib-app?command=SEARCH_PAGE">Search page</a>
            </li>
        </ul>
    </div>
</nav>

</body>
</html>
<script type="text/javascript">
    <%@include file="/WEB-INF/js/navigationpage.js"%>
</script>
<style>

    <%@include file="/WEB-INF/css/navigationpage.css"%>
</style>
