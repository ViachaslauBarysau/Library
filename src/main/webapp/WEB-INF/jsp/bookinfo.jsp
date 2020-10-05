<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta http-equiv="Cache-Control" content="no-cache">
  <title>Book info</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<!-- Book form starts -->
  <form method="post" id="bookform" enctype="multipart/form-data">
    <div>
      <div>
        <input type="text" id="bookId" class="form-control" size="60" name="id" value="${bookpagedto.bookDto.id}" hidden>
      </div>
      <div>
        <input type="text" class="form-control" size="60" name="currentCover" value="${bookpagedto.bookDto.covers.get(0)}" hidden>
      </div>
      <div>
        <p><img class="cover" class="form-control" id="cover" name="cover" src="images/${bookpagedto.bookDto.covers.get(0)}"></p>
      </div>
      <div>
        <label>Choose image to change cover:</label>
        <input type="file" class="form-control" name="file" accept="image/jpeg, image/png">
      </div>
      <div>
        <label>Title:</label>
        <input type="text" class="form-control" size="60" name="title" value="${bookpagedto.bookDto.title}" required>
      </div>
      <div>
        <label>Authors:</label>
        <c:if test="${empty bookpagedto.bookDto.authors}">
          <span name="span-authors"><input type="text" class="form-control" name="authors" size="60"
                                           onchange="validateAuthorsGenres(this)" required>
            <img class="remove" src="images/src/minus_PNG41.png" alt="Delete author" width="40" onclick="deleteAuthor(this)">
          </span>
        </c:if>
        <c:forEach var="author"  items="${bookpagedto.bookDto.authors}">
          <span name="span-authors"><input type="text" class="form-control" name="authors" size="60" value="${author}"
                                           onchange="validateAuthorsGenres(this)" required>
            <img class="remove" src="images/src/minus_PNG41.png" alt="Delete author" width="40" onclick="deleteAuthor(this)">
          </span>
         </c:forEach>
      </div>

      <div class="add-field">
        <img class="add-field" src="images/src/n8mnMsJKMVg1.png" alt="Add author" width="50" onclick="addAuthorField()">
      </div>
      <div>
        <label>Publisher:</label>
        <input type="text" class="form-control" name="publisher" size="60" value="${bookpagedto.bookDto.publisher}" required>
      </div>
      <div>
        <label>Publish year:</label>
        <c:choose>
          <c:when test="${bookpagedto.bookDto.id == 0}">
            <input type="number" type="number" min="0" max="2020" class="form-control" name="publishDate" size="60" required>
          </c:when>
          <c:otherwise>
            <input type="number" min="0" max="2020" class="form-control" name="publishDate" size="60"
                        value="${bookpagedto.bookDto.publishDate}" required />
          </c:otherwise>
        </c:choose>
      </div>
      <div>
        <label>Genres:</label>
        <c:if test="${empty bookpagedto.bookDto.genres}">
          <span name="span-genres"><input type="text" class="form-control" name="genres" size="60"
                                          onchange="validateAuthorsGenres(this)" required>
            <img class="remove" src="images/src/minus_PNG41.png" alt="Delete genre" width="40" onclick="deleteGenre(this)">
          </span>
        </c:if>
        <c:forEach var="genre" items="${bookpagedto.bookDto.genres}">
          <span name="span-genres"><input type="text" class="form-control" name="genres" size="60"
                                          onchange="validateAuthorsGenres(this)" value="${genre}" required>
            <img class="remove" src="images/src/minus_PNG41.png" alt="Delete genre" width="40" onclick="deleteGenre(this)">
          </span>
        </c:forEach>
      </div>
      <div>
        <img class="add-field" src="images/src/n8mnMsJKMVg1.png" alt="addGenre" width="50" onclick="addGenreField()">
      </div>
      <div>
        <label>Page count:</label>
        <c:choose>
          <c:when test="${bookpagedto.bookDto.id == 0}">
            <input type="number" min="1" max="9999" class="form-control" name="pageCount" size="60" required>
          </c:when>
          <c:otherwise>
            <input  type="number" min="1" max="9999" class="form-control" name="pageCount" size="60"
                  value="${bookpagedto.bookDto.pageCount}" required>
          </c:otherwise>
        </c:choose>
      </div>
      <div>
        <label>ISBN:</label>
        <input id="isbn" type="text" class="form-control" name="ISBN" size="60" value="${bookpagedto.bookDto.isbn}"
              onchange="validateIsbn()" required>
      </div>
      <div>
        <label>Description:</label>
        <textarea class="form-control" maxlength="980" name="description" cols="60" rows="12" required>
            ${bookpagedto.bookDto.description}
        </textarea>
      </div>
      <div>
        <label>Total amount:</label>
        <c:choose>
          <c:when test="${bookpagedto.bookDto.id == 0}">
            <input type="number" min="${bookpagedto.bookDto.totalAmount - bookpagedto.bookDto.availableAmount}"
                   max="999" class="form-control" size="60" id="totalAmount" onchange="onAmountChange()"
                   name="totalAmount" required>
          </c:when>
          <c:otherwise>
          <input type="number" min="${bookpagedto.bookDto.totalAmount - bookpagedto.bookDto.availableAmount}"
                 max="999" class="form-control" size="60" id="totalAmount" required name="totalAmount"
                 onchange="onAmountChange()" value="${bookpagedto.bookDto.totalAmount}">
          </c:otherwise>
        </c:choose>
      </div>
      <div>
        <c:choose>
          <c:when test="${bookpagedto.bookDto.id == 0}">
              <p><input type="text" class="form-control" id="availableAmount" size="60" name="availableAmount"
                        value="${bookpagedto.bookDto.availableAmount}" hidden></p>
          </c:when>
          <c:otherwise>
            <p><input type="text" class="form-control" id="availableAmount" size="60" name="availableAmount"
                      value="${bookpagedto.bookDto.availableAmount}" hidden></p>
            <label>Status:</label>
            <c:if test ="${bookpagedto.bookDto.totalAmount == 0}">
              <p><input type="text" class="form-control" id="bookstatus" size="60" value="Unavailable" readonly></p>
            </c:if>
            <c:if test ="${bookpagedto.bookDto.totalAmount != 0}">
              <c:if test ="${bookpagedto.bookDto.availableAmount <= 0}">
                <p><input type="text" class="form-control" id="bookstatus" size="60" readonly required
                          value="Unavailable (expected to become available on ${bookpagedto.nearestAvailableDate})"></p>
              </c:if>
              <c:if test ="${bookpagedto.bookDto.availableAmount > 0}">
                <p><input type="text" class="form-control" id="bookstatus" size="60" value="Available
                          ${bookpagedto.bookDto.availableAmount} out of ${bookpagedto.bookDto.totalAmount}" readonly></p>
              </c:if>
            </c:if>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
    <div>
      <button class="btn btn-primary" type="button" onclick="sendForm()"/>Save</button>
      <button class="btn btn-primary" formaction="lib-app?command=GET_BOOK_LIST&page=1" />Discard</button>
    </div>
  </form>
<!-- Book form ends -->
  <c:choose>
    <c:when test="${bookpagedto.bookDto.id == 0}">
      <button type="button" id="addButton" class="btn btn-primary" data-toggle="modal" data-target="#myModal"
                onclick="createNewReaderCard()" hidden />Add</button>
    </c:when>
    <c:otherwise>
      <!-- Borrow records table starts -->
      <label>Borrow Records List:</label>
      <table class="table table-striped" id="table">
        <thead class="thead-dark">
          <tr>
            <th>Email</th>
            <th>Name</th>
            <th>Borrow Date</th>
            <th>Due Date</th>
            <th>Return Date</th>
          </tr>
        </thead>
          <c:forEach var="readerCard" items="${bookpagedto.readerCards}" >
            <tr>
              <c:if test="${empty readerCard.returnDate}">
              <td><a href="#" id="email${readerCard.id}" onclick="openExistingReaderCard(${readerCard.id})"
                       data-toggle="modal" data-target="#myModal"
                       class="stretched-link">${readerCard.readerEmail}</a></td>
              </c:if>
              <c:if test="${not empty readerCard.returnDate}">
               <td><a href="#" id="link${readerCard.id}" onclick="openClosedReaderCard(${readerCard.id})"
                        data-toggle="modal" data-target="#myModal"
                        class="stretched-link">${readerCard.readerEmail}</a></td>
              </c:if>
              <td>${readerCard.readerName}</td>
              <td>${readerCard.borrowDate}</td>
              <td>${readerCard.dueDate}</td>
              <td id="rd${readerCard.id}" >${readerCard.returnDate}</td>
            </tr>
            </c:forEach>
        </table>
      <!-- Borrow records table ends -->
        <c:if test="${empty bookpagedto.readerCards}">
          <div id="records-message" align="center">No borrow records yet.</div>
        </c:if>
        <c:if test="${not empty bookpagedto.readerCards}">
          <div id="records-message" align="center" hidden>No borrow records yet.</div>
        </c:if>
        <!-- Button to Open the Modal -->
        <button type="button" id="addButton" class="btn btn-primary" data-toggle="modal" data-target="#myModal"
                onclick="createNewReaderCard()" />Add</button>
    </c:otherwise>
  </c:choose>
  <div class="container">
    <!-- The Modal -->
    <div class="modal fade" id="myModal">
      <div id="overlay" onclick="closeModal()"></div>
      <div class="modal-dialog">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">Borrow Record:</h4>
            <button type="button" class="close" onclick="closeModal()" data-dismiss="modal">×</button>
          </div>
          <!-- Modal body -->
          <div class="modal-body">
            <form id="modal-form" onclick="closeAutocomplete()">
              <div>
                <p><input id="readerCardId" type="text" size="40" hidden></p>
              </div>
              <div>
                <p><input id="readerId" type="text" size="40"  hidden></p>
              </div>
              <div>
                <label id="emailLabel" for="email">Email:</label>
                <p><input id="email" class="form-control" type="text" size="40" pattern=^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$
                          autocomplete="new-password" onkeyup="getEmailsByPattern(this.value)"  required></p>
              </div>
              <div id="autocomplete-container">
                <div id="autocomplete" class="hidden">
                  <ul>
                    <li>
                    </li>
                  </ul>
                </div>
              </div>
              <div>
                <label id="nameLabel" for="name">Full name:</label>
                <p><input id="name" class="form-control" type="text" size="40" required></p>
              </div>
              <div>
                <label id="borrowDateLabel" for="borrowdate">Borrow date:</label>
                <p><input id="borrowdate" class="form-control" type="text" size="40" readonly></p>
              </div>
              <div>
                <label id="timeperiodlabel" for="timeperiod">Time period:</label>
                <p><select id="timeperiod" class="form-control" name="timeperiod" required>
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="6">6</option>
                  <option value="12">12</option>
                </select></p>
              </div>
              <div>
                <label id="statuslabel" for="borrowingStatus">Choose status:</label>
                <p><select id="borrowingStatus" class="form-control" name="borrowingStatus" required>
                  <option value="borrowed"></option>
                  <option value="returned">Returned</option>
                  <option value="damaged">Returned and damaged</option>
                  <option value="lost">Lost</option>
                </select></p>
              </div>
              <div>
                <label id="commentlabel" for="comment">Comment:</label>
                <p><input id="comment" class="form-control" type="text" size="40"></p>
                <div class="modal-footer">
                  <button type="button" id="saveButton" class="btn btn-primary" data-dismiss="modal">Save</button>
                  <button type="button" class="btn btn-primary" onclick="closeModal()" data-dismiss="modal">Close</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div id="myErrorModal" class="modal">
    <div class="modal-cont">
      <p id="message" align="center">No books available!</p>
    </div>
  </div>
  <div id="snackbar"></div>
</body>
</html>
<script type="text/javascript">
  <%@include file="/WEB-INF/js/bookinfo.js"%>
  <%@include file="/WEB-INF/js/booklist-modal-parameters.js"%>
  <%@include file="/WEB-INF/js/booklist-modal-functions.js"%>
  <%@include file="/WEB-INF/js/validation.js"%>
</script>
<style>
  <%@include file="/WEB-INF/css/bookinfo.css"%>
</style>
