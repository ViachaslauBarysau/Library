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
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>Title</title>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>--%>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>

<form method="post" id="bookform" enctype="multipart/form-data">

    <input type="text" class="form-control" size="60" name="id" value="${bookpagedto.bookDto.id}" hidden />
    <input type="text" class="form-control" size="60" name="currentCover" value="${bookpagedto.bookDto.covers.get(0)}" hidden />
    <p><img class="cover" class="form-control" id="cover" name="cover" src="images/${bookpagedto.bookDto.covers.get(0)}"></p>
    Choose image to change cover:
    <p><input type="file" class="form-control" name="file" accept="image/jpeg, image/png" /></p>
    Title
    <p><input type="text" class="form-control" size="60" name="title" value="${bookpagedto.bookDto.title}" required /></p>
    Authors
    <c:if test="${empty bookpagedto.bookDto.authors}">
        <p><input type="text" class="form-control" name="authors" size="60" required></p>
    </c:if>
    <c:forEach var="author"  items="${bookpagedto.bookDto.authors}" >
        <p><input type="text" class="form-control" name="authors" size="60" value="${author}" required /></p>
    </c:forEach>
    Publisher
    <p><input type="text" class="form-control" name="publisher" size="60" value="${bookpagedto.bookDto.publisher}" required /></p>
    Publish date
    <c:choose>
        <c:when test="${bookpagedto.bookDto.id == 0}">
            <p><input type="text" class="form-control" name="publishDate" size="60" required /></p>
        </c:when>
        <c:otherwise>
            <p><input type="text" class="form-control" name="publishDate" size="60" value="${bookpagedto.bookDto.publishDate}" required /></p>
        </c:otherwise>
    </c:choose>
    Genres
    <c:if test="${empty bookpagedto.bookDto.genres}">
        <p><input type="text" class="form-control" name="genres" size="60" required/></p>
    </c:if>
    <c:forEach var="genre" items="${bookpagedto.bookDto.genres}">
        <p><input type="text" class="form-control" name="genres" size="60" value="${genre}" required /></p>
    </c:forEach>
    Page count
    <c:choose>
        <c:when test="${bookpagedto.bookDto.id == 0}">
            <p><input type="text" class="form-control" name="pageCount" size="60" required /></p>
        </c:when>
        <c:otherwise>
            <p><input  type="text" class="form-control" name="pageCount" size="60" value="${bookpagedto.bookDto.pageCount}" required /></p>
        </c:otherwise>
    </c:choose>
    ISBN
    <p><input type="text" class="form-control" name="ISBN" size="60" value="${bookpagedto.bookDto.ISBN}" required /></p>
    Description
    <p><textarea class="form-control" maxlength="420" name="description" cols="60" rows="12" required>
    ${bookpagedto.bookDto.description}
    </textarea></p>
    Total amount
    <c:choose>
        <c:when test="${bookpagedto.bookDto.id == 0}">
            <p><input type="text" class="form-control" size="60" id="totalAmount" onchange="onAmountChange(this.value)" name="totalAmount" equired /></p>
        </c:when>
        <c:otherwise>
            <p><input type="text" class="form-control" size="60" id="totalAmount" onchange="onAmountChange(this.value)" name="totalAmount" value="${bookpagedto.bookDto.totalAmount}" required /></p>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${bookpagedto.bookDto.id == 0}">
            <p><input type="text" class="form-control" id="availableAmount" size="60" name="availableAmount" value="${bookpagedto.bookDto.availableAmount}" hidden /></p>
        </c:when>
        <c:otherwise>
            <p><input type="text" class="form-control" id="availableAmount" size="60" name="availableAmount" value="${bookpagedto.bookDto.availableAmount}" hidden />
            Status
            <c:if test ="${bookpagedto.bookDto.totalAmount == 0}">
                <p><input type="text" class="form-control" id="bookstatus" size="60" value="Unavailable" readonly required /></p>
            </c:if>
            <c:if test ="${bookpagedto.bookDto.totalAmount != 0}">
                <c:if test ="${bookpagedto.bookDto.availableAmount <= 0}">
                    <p><input type="text" class="form-control" id="bookstatus" size="60" value="Unavailable (expected to become available on ${bookpagedto.nearestAvailableDate})" readonly required /></p>
                </c:if>
                <c:if test ="${bookpagedto.bookDto.availableAmount > 0}">
                    <p><input type="text" class="form-control" id="bookstatus" size="60" value="Available ${bookpagedto.bookDto.availableAmount} out of ${bookpagedto.bookDto.totalAmount}" readonly></p>
                </c:if>
            </c:if>
        </c:otherwise>
    </c:choose>

    <button class="btn btn-primary" type="submit" formaction="javascript:sendForm()"/>
    Save
    </button>
    <button class="btn btn-primary" formaction="lib-app?command=GET_BOOK_LIST&page=1" />
    Dismiss
    </button>
</form>

Borrow Records List:
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
        <tr id>

                <c:if test="${empty readerCard.returnDate}">
                    <td><a href="#" id="link${readerCard.id}" onclick="openExistingReaderCard(${readerCard.id})" data-toggle="modal" data-target="#myModal" class="stretched-link">${readerCard.readerEmail}</a></td>
                </c:if>
                <c:if test="${not empty readerCard.returnDate}">
                    <td><a href="#" id="link${readerCard.id}" onclick="openClosedReaderCard(${readerCard.id})" data-toggle="modal" data-target="#myModal" class="stretched-link">${readerCard.readerEmail}</a></td>
                </c:if>

            <td>${readerCard.readerName}</td>
            <td>${readerCard.borrowDate}</td>
            <td>${readerCard.dueDate}</td>
            <td id="rd${readerCard.id}" >${readerCard.returnDate}</td>
        </tr>
    </c:forEach>
</table>


<div class="container">
<%--    <h2>Fading Modal</h2>--%>
<%--    <p>Add the "fade" class to the modal container if you want the modal to fade in on open and fade out on close.</p>--%>

    <!-- Button to Open the Modal -->
    <button type="button" id="addButton" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="openNewReaderCard()">
        Add
    </button>

    <!-- The Modal -->
    <div class="modal fade" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">Borrow Record:</h4>
                    <button type="button" class="close" onclick="closeModal()" data-dismiss="modal">×</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">

                    <form>
                        <p><input id="readerCardId" type="text" size="40" hidden></p>
                        <p><input id="readerId" type="text" size="40"  hidden></p>
                        <label id="emailLabel" for="email">Email:</label>
                        <p><input id="email" class="form-control" type="email" size="40"  onkeyup="getEmailsByPattern(this.value)"  required></p>
                        <label id="nameLabel" for="name">Full name:</label>
                        <p><input id="name" class="form-control" type="text" size="40" required></p>
                        <label id="borrowDateLabel" for="borrowdate">Borrow date:</label>
                        <p><input id="borrowdate" class="form-control" type="text" size="40" readonly></p>
                        <label id="timeperiodlabel" for="timeperiod">Time period:</label>
                        <p><select id="timeperiod" class="form-control" name="timeperiod" required>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="6">6</option>
                            <option value="12">12</option>
                        </select></p>
                        <label id="statuslabel" for="borrowingStatus">Choose status:</label>
                        <p><select id="borrowingStatus" class="form-control" name="borrowingStatus" required>
                            <option value="borrowed"></option>
                            <option value="returned">Returned</option>
                            <option value="damaged">Returned and damaged</option>
                            <option value="lost">Lost</option>
                        </select></p>
                        <label id="commentlabel" for="comment">Comment:</label>
                        <p><input id="comment" class="form-control" type="text" size="40"></p>
                        <div class="modal-footer">
                            <button type="button" id="saveButton" class="btn btn-primary" onclick="saveReaderCard()" data-dismiss="modal">Save</button>
                            <button type="button" class="btn btn-primary" onclick="closeModal()" data-dismiss="modal">Close</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="modalbackground"></div>

</body>
</html>
<script type="text/javascript">
    <%@include file="/WEB-INF/js/bookinfo.js"%>
</script>
<style>

    <%@include file="/WEB-INF/css/bookinfo.css"%>
</style>
