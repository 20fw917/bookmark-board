<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.104.2">
    <title>로그인</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/sign-in/">
    <!-- Bootstrap core CSS -->
    <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
    <link href="${pageContext.request.contextPath}/static/css/login.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/common/floating_labels.css" rel="stylesheet">
    <sec:csrfMetaTags/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container text-center">
    <form:form class="form-signin" action="${pageContext.request.contextPath}/login" method="post">
        <h1 class="h3 mb-3 font-weight-normal">로그인</h1>
        <c:if test="${fail eq true}">
            <div class="alert alert-danger" role="alert">
                계정 정보가 일치하지 않습니다. 다시 확인해주세요.<br>
                회원이 아니라면 <a href="/user/register" class="alert-link">회원 가입</a> 해주세요.
            </div>
        </c:if>

    <div class="text-start">
        <div class="form-label-group position-relative">
            <input type="text" name="inputId" id="inputId"
                   class="form-control" placeholder="아이디" maxlength='30' required autofocus>
            <label for="inputId">아이디</label>
        </div>
        <div class="form-label-group position-relative">
            <input type="password" name="inputPassword" id="inputPassword" class="form-control"
                   placeholder="Password" maxlength='30' autocomplete="on" required>
            <label for="inputPassword">비밀번호</label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit">로그인</button><br><br>
        <a href="${pageContext.request.contextPath}/user/register"><button type="button" class="w-100 btn btn-lg btn-secondary">회원 가입</button></a>
        <p class="mt-5 mb-3 text-muted">&copy; 2022-2023</p>
    </div>
    </form:form>
</div>
</body>
</html>
