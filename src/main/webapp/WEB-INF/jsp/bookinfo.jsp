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
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>--%>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>

<form method="post" id="bookform" enctype="multipart/form-data">

    <input type="text" size="60" name="id" value="${bookpagedto.bookDto.id}" hidden />
    <input type="text" size="60" name="currentCover" value="${bookpagedto.bookDto.covers.get(0)}" hidden />
    <p><img class="cover" id="cover" name="cover" src="images/${bookpagedto.bookDto.covers.get(0)}"></p>
    Choose image to change cover:
    <p><input type="file" name="file" accept="image/jpeg, image/png" /></p>
    Title
    <p><input type="text" size="60" name="title" value="${bookpagedto.bookDto.title}" required /></p>
    Authors
    <c:if test="${empty bookpagedto.bookDto.authors}">
        <p><input type="text" name="authors" size="60" required></p>
    </c:if>
    <c:forEach var="author"  items="${bookpagedto.bookDto.authors}" >
        <p><input type="text" name="authors" size="60" value="${author}" required /></p>
    </c:forEach>
    Publisher
    <p><input type="text" name="publisher" size="60" value="${bookpagedto.bookDto.publisher}" required /></p>
    Publish date
    <c:choose>
        <c:when test="${bookpagedto.bookDto.id == 0}">
            <p><input type="text" name="publishDate" size="60" required /></p>
        </c:when>
        <c:otherwise>
            <p><input type="text" name="publishDate" size="60" value="${bookpagedto.bookDto.publishDate}" required /></p>
        </c:otherwise>
    </c:choose>
    Genres
    <c:if test="${empty bookpagedto.bookDto.genres}">
        <p><input type="text" name="genres" size="60" required/></p>
    </c:if>
    <c:forEach var="genre" items="${bookpagedto.bookDto.genres}">
        <p><input type="text" name="genres" size="60" value="${genre}" required /></p>
    </c:forEach>
    Page count
    <c:choose>
        <c:when test="${bookpagedto.bookDto.id == 0}">
            <p><input type="text" name="pageCount" size="60" required /></p>
        </c:when>
        <c:otherwise>
            <p><input type="text" name="pageCount" size="60" value="${bookpagedto.bookDto.pageCount}" required /></p>
        </c:otherwise>
    </c:choose>
    ISBN
    <p><input type="text" name="ISBN" size="60" value="${bookpagedto.bookDto.ISBN}" required /></p>
    Description
    <p><textarea maxlength="400" name="description" cols="60" rows="12" required>
    ${bookpagedto.bookDto.description}
    </textarea></p>
    Total amount
    <c:choose>
        <c:when test="${bookpagedto.bookDto.id == 0}">
            <p><input type="text" size="60" id="totalAmount" name="totalAmount" equired /></p>
        </c:when>
        <c:otherwise>
            <p><input type="text" size="60" id="totalAmount" name="totalAmount" value="${bookpagedto.bookDto.totalAmount}" required /></p>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${bookpagedto.bookDto.id == 0}">
            <p><input type="text" id="availableAmount" size="60" name="availableAmount" value="${bookpagedto.bookDto.availableAmount}" hidden /></p>
        </c:when>
        <c:otherwise>
            <p><input type="text" id="availableAmount" size="60" name="availableAmount" value="${bookpagedto.bookDto.availableAmount}" hidden />
            Status
            <c:if test ="${bookpagedto.bookDto.availableAmount <= 0}">
                <p><input type="text" id="bookstatus" size="60" value="Unavailable (expected to become available on ${bookpagedto.nearestAvailableDate})" readonly required /></p>
            </c:if>
            <c:if test ="${bookpagedto.bookDto.availableAmount > 0}">
                <p><input type="text" id="bookstatus" size="60" value="Available ${bookpagedto.bookDto.availableAmount} out of ${bookpagedto.bookDto.totalAmount}" readonly></p>
            </c:if>
        </c:otherwise>
    </c:choose>


<%--    <button type="submit" formaction="lib-app?command=ADD_EDIT_BOOK" />--%>
    <button type="submit" formaction="javascript:sendForm()"/>
    Save
    </button>
    <button formaction="lib-app?command=GET_BOOK_LIST&page=1" />
    Dismiss
    </button>
</form>

Borrow Records List:
<table border="1" id="table">
    <tr>
        <th>Email</th>
        <th>Name</th>
        <th>Borrow Date</th>
        <th>Due Date</th>
        <th>Return Date</th>
    </tr>
    <c:forEach var="readerCard" items="${bookpagedto.readerCards}" >
        <tr id>
            <td><a href="#" onclick="openReaderCard(${readerCard.id})" data-toggle="modal" data-target="#myModal" class="stretched-link">${readerCard.readerEmail}</a></td>
            <td>${readerCard.readerName}</td>
            <td>${readerCard.borrowDate}</td>
            <td>${readerCard.dueDate}</td>
            <td id="${readerCard.id}" >${readerCard.returnDate}</td>
        </tr>
    </c:forEach>
</table>


<div class="container">
<%--    <h2>Fading Modal</h2>--%>
<%--    <p>Add the "fade" class to the modal container if you want the modal to fade in on open and fade out on close.</p>--%>

    <!-- Button to Open the Modal -->
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="openReaderCard(0)">
        Add
    </button>

    <!-- The Modal -->
    <div class="modal fade" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">Borrow Record:</h4>
                    <button type="button" class="close" data-dismiss="modal">×</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">

                    <form>
                        <p><input id="readerCardId" type="text" size="40"  hidden></p>
                        <p><input id="readerId" type="text" size="40"  hidden></p>
                        Email:
                        <p><input id="email" type="email" size="40"  required></p>
                        Full name:
                        <p><input id="name" type="text" size="40" required></p>
                        Borrow date:
                        <p><input id="borrowdate" type="text" size="40" readonly></p>
                        <label id="timeperiodlabel" for="timeperiod">Time period:</label>
                        <p><select id="timeperiod" name="timeperiod" required>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="6">6</option>
                            <option value="12">12</option>
                        </select></p>
                        <label id="statuslabel" for="borrowingStatus">Choose status:</label>
                        <p><select id="borrowingStatus" name="borrowingStatus">
                            <option value="nostatus"></option>
                            <option value="returned">Returned</option>
                            <option value="damaged">Returned and damaged</option>
                            <option value="lost">Lost</option>
                        </select></p>
                        Comment:
                        <p><input id="comment" type="text" size="40"></p>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-danger" onclick="saveReaderCard()" data-dismiss="modal">Save</button>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
<script type="text/javascript">
    <%@include file="/WEB-INF/js/bookinfo.js"%>
</script>
<style>

    <%@include file="/WEB-INF/css/bookinfo.css"%>
</style>
