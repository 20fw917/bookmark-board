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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/Register.js"></script>
    <sec:csrfMetaTags/>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container">
    <h1>내가 등록한 북마크가 표시되는 공간</h1>
</div>
</body>
</html>