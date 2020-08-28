<%--
  Created by IntelliJ IDEA.
  User: Lemongrass
  Date: 27.08.2020
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <img class="imagepreview" id="picture" src="images/glass.jpg">
    <div class="grid" align="center">
        <c:forEach var="cover" items="${book.covers}">
            <div class="cover">
                <img class="images" src="images/${cover}" alt="${cover}" onclick="ShowImage(this.src)">
            </div>
        </c:forEach>
    </div>
    Title
    <p><input type="text" size="40" value="${book.title}" required></p>
    Authors
    <c:forEach var="author" items="${book.authors}" >
        <p><input type="text" size="40" value="${author}" required></p>
    </c:forEach>
    Publisher
    <p><input type="text" size="40" value="${book.publisher}" required></p>
    Publish date
    <p><input type="text" size="40" value="${book.publishDate}" required></p>
    Genres
    <c:forEach var="genre" items="${book.genres}">
        <p><input type="text" size="40" value="${genre}" ></p>
    </c:forEach>
    Page count
    <p><input type="text" size="40" value="${book.pageCount}" required></p>
    ISBN
    <p><input type="text" size="40" value="${book.ISBN}" required></p>
    Description
    <p><textarea maxlength="400" cols="42" rows="12" required>
    ${book.description}
    </textarea></p>
    Total amount
    <p><input type="text" size="40" value="${book.totalAmount}" required></p>
    Status
    <p><input type="text" size="40" value="Available ${book.availableAmount} out of ${book.totalAmount}" readonly></p>

    <button type="submit" formaction="lib-app?command=SAVE_BOOK" />
    Save
    </button>
    <button type="submit" formaction="lib-app?command=DISMISS" />
    Dismiss
    </button>

</form>
</body>
</html>
<script type="text/javascript">
    <%@include file="/WEB-INF/js/bookinfo.js"%>
</script>
<style>
    <%@include file="/WEB-INF/css/bookinfo.css"%>
</style>
