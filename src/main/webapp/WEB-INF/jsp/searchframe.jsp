<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Search Page</title>
</head>
<body>
  <form method="post">
    <div>
      <label id="titleLabel" for="title">Title:</label>
      <p><input type="text" class="form-control" name="title" id="title" size="40" value="${title}"></p>
    </div>
    <div>
      <label id="authorLabel" for="author">Authors:</label>
      <p><input type="text" class="form-control" name="author" id="author"  size="40" value="${author}"></p>
    </div>
    <div>
      <label id="genreLabel"  for="genre">Genres:</label>
      <p><input type="text" class="form-control" name="genre" id="genre" size="40" value="${genre}"></p>
    </div>
    <div>
      <label id="descriptionLabel" for="description">Description:</label>
      <p><input type="text" class="form-control" name="description" id="description" size="40" value="${description}"></p>
    </div>
    <p><button type="submit" class="btn btn-primary"  formaction="lib-app?command=SEARCH_BOOK&page=1"/>Search</button></p>
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
           <td><a class="stretched-link" href="lib-app?command=BOOK_PAGE&id=${book.id}">${book.title}</a>
           </td>
           <td><c:forEach var="author" items="${book.authors}">
                 ${author}<br>
               </c:forEach>
           </td>
           <td>${book.publishDate}</td>
           <td>${book.availableAmount}</td>
        </tr>
      </c:forEach>
   </table>
  <c:if test ="${empty books}">
    <div align="center"><p>Search results.</p></div>
  </c:if>
    <button type="submit" class="btn btn-primary" formaction="lib-app?command=ADD_BOOK_PAGE" />Add Book</button>
    <button type="submit" class="btn btn-primary"
            formaction="lib-app?command=DELETE_SEARCHED_BOOK&title=${title}&author=${author}&genre=${genre}&description=${description}&page=${pageNumber}" />
    Delete Book(-s)
    </button>
    <c:if test ="${not empty pageNumber}">
      <c:if test ="${pageNumber > 1}">
        <button type="submit" class="btn btn-primary"
                formaction="lib-app?command=SEARCH_BOOK&title=${title}&author=${author}&genre=${genre}&description=${description}&page=${pageNumber-1}" />
          &lt;
        </button>
        ${pageNumber} of ${pageCount}
        <button type="submit" class="btn btn-primary"
                formaction="lib-app?command=SEARCH_BOOK&title=${title}&author=${author}&genre=${genre}&description=${description}&page=${pageNumber+1}" disabled/>
          &gt;
        </button>
      </c:if>
      <c:if test ="${pageNumber < pageCount}">
        <button type="submit" class="btn btn-primary"
                formaction="lib-app?command=SEARCH_BOOK&title=${title}&author=${author}&genre=${genre}&description=${description}&page=${pageNumber-1}" disabled/>
          &lt;
        </button>
        ${pageNumber} of ${pageCount}
        <button type="submit" class="btn btn-primary"
                formaction="lib-app?command=SEARCH_BOOK&title=${title}&author=${author}&genre=${genre}&description=${description}&page=${pageNumber+1}" />
          &gt;
        </button>
      </c:if>
    </c:if>
  </form>
</body>
</html>
