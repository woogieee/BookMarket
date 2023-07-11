<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <!-- JSTL의 function태그를 jsp에서 사용함 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
<title>도서 목록</title>
</head>
<body>
	<nav class="navbar navbar-expand navbar-dark bg-dark">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="./home">Home</a>
			</div>
		</div>
	</nav>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">도서 목록</h1>
		</div>
	</div>
	<div class="container">
		<div class="row" align="center">
			<c:forEach items="${bookList}" var="book">		<!-- JSTL의 for each구문을 이용한 반복, ${bookList}는 BookController에서 전달된 모델 데이터를 var 속성 값인 book으로 재정의 함 -->
				<div class="col-md-4">
					<!--  -->
					<img src="<c:url value="/resources/images/${book.bookId}.png"/>" style="width: 60%"/>
					<!-- 
					<c:choose>
						<c:when test="${book.getBookImage() == null}">
							<img src="<c:url value="C:\\upload\\${book.getBookId()}.png"/>" style="width: 60%"/>
						</c:when>
						<c:otherwise>
							<img src="<c:url value="C:\\upload\\${book.getBookImage().getOriginalFilename()}"/>" style="width: 60%"/>
						</c:otherwise>
					</c:choose>
					-->
					<h3>${book.name}</h3>
					<p>${book.author}
						<br>${book.publisher} | ${book.releaseDate}</p>
					<p align=left>${fn:substring(book.description, 0, 100)}...</p>
					<p>${book.unitPrice}원</p>
					<p><a href="<c:url value="/books/book?id=${book.bookId}"/>" class="btn btn-secondary" role="button">상세정보 &raquo;</a>
				</div>
			</c:forEach>
		</div>
		<hr>
		<footer>
			<p>&copy; BookMarket</p>
		</footer>
	</div>
</body>
</html>