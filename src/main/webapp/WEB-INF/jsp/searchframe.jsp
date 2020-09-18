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
    <label id="titleLabel" for="title">Title:</label>
    <p><input type="text" class="form-control" name="title" id="title" size="40" value="${title}"></p>
    <label id="authorLabel" for="author">Authors:</label>
    <p><input type="text" class="form-control" name="author" id="author"  size="40" value="${author}"></p>
    <label id="genreLabel"  for="genre">Genres:</label>
    <p><input type="text" class="form-control" name="genre" id="genre" size="40" value="${genre}"></p>
    <label id="descriptionLabel" for="description">Description:</label>
    <p><input type="text" class="form-control" name="description" id="description" size="40" value="${description}"></p>

    <button type="submit" class="btn btn-primary"  formaction="lib-app?command=SEARCH_BOOK"/>
    Search
    </button>
    <p></p>
    <table class="table table-striped">
        <thead class="thead-dark">
            <tr>
                <th></th>
                <th>Title</th>
                <th>Authors</th>
                <th>Publish date</th>
                <th>Amount available</th>
            </tr>
        </thead>
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

    <button type="submit" class="btn btn-primary" formaction="lib-app?command=ADD_BOOK_PAGE" />
    Add Book
    </button>
    <button type="submit" class="btn btn-primary" formaction="lib-app?command=DELETE_SEARCHED_BOOK&title=${title}&author=${author}&genre=${genre}&description=${description}&page=${pageNumber}" />
    Delete Book(-s)
    </button>

    <c:if test ="${not empty pageNumber}">
        <c:if test ="${pageNumber > 1}">
            <button type="submit" class="btn btn-primary" formaction="lib-app?command=SEARCH_BOOK&title=${title}&author=${author}&genre=${genre}&description=${description}&page=${pageNumber-1}" />
            &lt;
            </button>
            ${pageNumber}
            <button type="submit" class="btn btn-primary" formaction="lib-app?command=SEARCH_BOOK&title=${title}&author=${author}&genre=${genre}&description=${description}&page=${pageNumber+1}" disabled/>
            &gt;
            </button>
        </c:if>
        <c:if test ="${pageNumber < pageCount}">
            <button type="submit" class="btn btn-primary" formaction="lib-app?command=SEARCH_BOOK&title=${title}&author=${author}&genre=${genre}&description=${description}&page=${pageNumber-1}" disabled/>
            &lt;
            </button>
            ${pageNumber}
            <button type="submit" class="btn btn-primary" formaction="lib-app?command=SEARCH_BOOK&title=${title}&author=${author}&genre=${genre}&description=${description}&page=${pageNumber+1}" />
            &gt;
            </button>
        </c:if>
    </c:if>
</form>

</body>
</html>
