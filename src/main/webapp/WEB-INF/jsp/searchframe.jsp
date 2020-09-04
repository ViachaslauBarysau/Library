<%--
  Created by IntelliJ IDEA.
  User: Lemongrass
  Date: 28.08.2020
  Time: 14:49
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

<%--    <input type="text" name="command" value="SEARCH_BOOK" size="40" hidden>--%>
    Title
    <p><input type="text" name="title" size="40" value="${title}"></p>
    Authors
    <p><input type="text" name="author" size="40" value="${author}"></p>
    Genres
    <p><input type="text" name="genre" size="40" value="${genre}"></p>
    Description
    <p><input type="text" name="description" size="40" value="${description}"></p>

    <button type="submit" formaction="lib-app?command=SEARCH_BOOK"/>
    Search
    </button>

    <table>
        <tr>
            <th></th>
            <th>Title</th>
            <th>Authors</th>
            <th>Publish date</th>
            <th>Amount available</th>
        </tr>
        <c:forEach var="book" items="${books}">
            <tr>
                <td><input type="checkbox" name="bookid" value="${book.id}"></td>
                <td>
                    <a class="stretched-link" href="lib-app?command=BOOK_PAGE&id=${book.id}">
                            ${book.title}
                    </a>
                </td>
                <td><c:forEach var="author" items="${book.authors}">
                    ${author}
                </c:forEach>
                </td>
                <td>${book.publishDate}</td>
                <td>${book.availableAmount}</td>
            </tr>
        </c:forEach>
    </table>

    <button type="submit" formaction="lib-app?command=ADD_BOOK_PAGE" />
    Add Book
    </button>
    <button type="submit" formaction="lib-app?command=DELETE_SEARCHED_BOOK&page=${pageNumber}" />
    Delete Book(-s)
    </button>
    <c:if test ="${pageNumber > 1}">
        <button type="submit" formaction="lib-app?command=SEARCH_BOOK&page=${pageNumber-1}" />
        &lt;
        </button>
    </c:if>
    page ${pageNumber}
    <c:if test ="${pageNumber < pageCount}">
        <button type="submit" formaction="lib-app?command=SEARCH_BOOK&page=${pageNumber+1}" />
        &gt;
        </button>
    </c:if>
</form>





<%--<form method="get">--%>
<%--    <input type="text" name="command" value="SEARCH_BOOK" size="40" hidden>--%>
<%--    Title--%>
<%--    <p><input type="text" name="title" size="40"></p>--%>
<%--    Authors--%>
<%--    <p><input type="text" name="author" size="40"></p>--%>
<%--    Genres--%>
<%--    <p><input type="text" name="genre" size="40"></p>--%>
<%--    Description--%>
<%--    <p><input type="text" name="description" size="40"></p>--%>

<%--    <input type="text" name="page" value="1" size="40" hidden>--%>





<%--    <button type="submit"/>--%>
<%--    Search--%>
<%--    </button>--%>

<%--</form>--%>
</body>
</html>
