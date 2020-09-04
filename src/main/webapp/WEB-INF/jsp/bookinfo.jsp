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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<form method="post" enctype="multipart/form-data" name="bookForm">

    <p><img class="cover" id="cover" src="images/${bookpagedto.bookDto.covers.get(0)}"></p>
    Choose image to change cover:
    <p><input type="file" name="file" size="40"/></p>
    Title
    <p><input type="text" size="40" value="${bookpagedto.bookDto.title}" required></p>
    Authors
    <c:if test="${empty bookpagedto.bookDto.authors}">
        <p><input type="text" size="40" required></p>
    </c:if>
    <c:forEach var="author" items="${bookpagedto.bookDto.authors}" >
        <p><input type="text" size="40" value="${author}" required></p>
    </c:forEach>
    Publisher
    <p><input type="text" size="40" value="${bookpagedto.bookDto.publisher}" required></p>
    Publish date
    <p><input type="text" size="40" value="${bookpagedto.bookDto.publishDate}" required></p>
    Genres
    <c:if test="${empty bookpagedto.bookDto.genres}">
        <p><input type="text" size="40" required></p>
    </c:if>
    <c:forEach var="genre" items="${bookpagedto.bookDto.genres}">
        <p><input type="text" size="40" value="${genre}" ></p>
    </c:forEach>
    Page count
    <p><input type="text" size="40" value="${bookpagedto.bookDto.pageCount}" required></p>
    ISBN
    <p><input type="text" size="40" value="${bookpagedto.bookDto.ISBN}" required></p>
    Description
    <p><textarea maxlength="400" cols="42" rows="12" required>
    ${bookpagedto.bookDto.description}
    </textarea></p>
    Total amount
    <p><input type="text" size="40" value="${bookpagedto.bookDto.totalAmount}" required></p>
    <p><input type="text" size="40" value="${bookpagedto.bookDto.availableAmount}" hidden></p>
    Status
    <c:if test ="${bookpagedto.bookDto.availableAmount <= 0}">
        <p><input type="text" size="40" value="Unavailable" readonly></p>
    </c:if>
    <c:if test ="${bookpagedto.bookDto.availableAmount > 0}">
        <p><input type="text" size="40" value="Available ${bookpagedto.bookDto.availableAmount} out of ${bookpagedto.bookDto.totalAmount}" readonly></p>
    </c:if>

    <button type="submit" formaction="lib-app?command=SAVE_BOOK" />
    Save
    </button>
    <button type="submit" formaction="lib-app?command=DISMISS" />
    Dismiss
    </button>

</form>

Borrow Records List

<table>
    <tr>
        <th>Email</th>
        <th>Name</th>
        <th>Borrow Date</th>
        <th>Due Date</th>
        <th>Return Date</th>
    </tr>
    <c:forEach var="readerCard" items="${bookpagedto.readerCards}" >
        <tr>
            <td><a href="${readerCard.readerId}" id="${readerCard.readerId}" data-toggle="modal" data-target="#myModal" class="stretched-link">${readerCard.readerEmail}</a></td>
            <td>${readerCard.readerName}</td>
            <td>${readerCard.borrowDate}</td>
            <td>${readerCard.dueDate}</td>
            <td>${readerCard.returnDate}</td>
        </tr>
    </c:forEach>
</table>


<div class="container">
<%--    <h2>Fading Modal</h2>--%>
<%--    <p>Add the "fade" class to the modal container if you want the modal to fade in on open and fade out on close.</p>--%>

    <!-- Button to Open the Modal -->
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="Showmodal()">
        Add
    </button>

    <!-- The Modal -->
    <div class="modal fade" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">Modal Heading</h4>
                    <button type="button" class="close" data-dismiss="modal">×</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">

                    <form>
                        <p><input id="firstrow" type="text" size="40"  required></p>
                        <p><input id="secondrow" type="text" size="40" required></p>
                        <p><input id="thirdrow" type="text" size="40" required></p>
                        <p><input id="fourthrow" type="text" size="40" required></p>
                    </form>

                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger">Save</button>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
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
