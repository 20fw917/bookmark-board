<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>내 북마크 리스트</title>
    <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
    <link href="${pageContext.request.contextPath}/static/css/common/floating_labels.css" rel="stylesheet">
    <sec:csrfMetaTags/>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container">
    <h1>내가 등록한 북마크가 표시되는 공간</h1>
</div>
<div class="container">
    <div class="container">
        <div class="card-body">
            <h5 class="card-title">네이버k</h5>
            <p class="card-text">여기는 내가 찾는 네이버!</p>
            <a href="www.naver.com" class="btn btn-primary stretched-link">www.naver.com</a>
        </div>
    </div>

    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th scope="col">제목</th>
            <th scope="col">메모</th>
            <th scope="col">주소</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${items}" var="item">
            <tr>
                <td>${item.title}</td>
                <td>${item.memo}</td>
                <td>${item.url}</td>
            </tr>
        </c:forEach>
        <tr>
            <td>안녕하세요. 가입했습니다.</td>
            <td>코코블루</td>
            <td>3</td>
        </tr>
        <tr>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
        </tr>
        <tr>
            <td colspan="2">Larry the Bird</td>
            <td>@twitter</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>