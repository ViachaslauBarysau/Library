<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Navigation page</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-sm bg-light navbar-light">
    <a class="navbar-brand nav" href="lib-app?command=GET_BOOK_LIST&page=1">Library Management</a>
    <div id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav" id="main-nav" href="lib-app?command=GET_BOOK_LIST&page=1">Main page</a>
            </li>
            <li class="nav-item">
                <a class="nav" id="search-nav" href="lib-app?command=SEARCH_EXECUTE">Search page</a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">

        </ul>
    </div>
    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav" id="logout" href="auth">Logout</a>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>
<style>
    <%@include file="/WEB-INF/css/navigationpage.css"%>
</style>
