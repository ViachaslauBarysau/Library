<%--
  Created by IntelliJ IDEA.
  User: Lemongrass
  Date: 24.08.2020
  Time: 19:54
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
                    <a href="lib-app?command=BOOK_PAGE&id=${book.id}">
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
    <button type="submit" formaction="lib-app?command=ADD_BOOK" />
    Add Book
    </button>
    <button type="submit" formaction="lib-app?command=DELETE_BOOK" />
    Delete Book(-s)
    </button>
    <c:if test ="${pageNumber > 1}">
        <button type="submit" formaction="lib-app?command=GET_BOOK_LIST&page=${pageNumber - 1}" />
        &lt;
        </button>
    </c:if>
    ${pageNumber}
    <c:if test ="${pageNumber < pageCount}">
        <button type="submit" formaction="lib-app?command=GET_BOOK_LIST&page=${pageNumber + 1}" />
        &gt;
        </button>
    </c:if>
</form>

</body>
</html>
