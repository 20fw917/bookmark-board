<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>공유 폴더 화면</title>
    <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container">
    <%--  툴바 --%>
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3">
        <!--중앙 위 타이틀-->
        <h2 class="h2 bi bi-asterisk"> 공유 폴더</h2>
        <div class="btn-toolbar mt-3 mb-2 mb-md-3"> <!--mb-md- 메인 멘트 간격조정-->
            <div class="btn-group me-3"> <!--me 버튼 간격-->

                <!--최신순 버튼-->
                <a href="#" class="btn btn btn-outline-secondary">
                    <i class="bi bi-clock"></i>
                    최신순
                </a>

                <!--추천순 버튼-->
                <a href="#" class="btn btn btn-outline-secondary">
                    <i class="bi bi-hand-thumbs-up"></i>
                    추천순
                </a>
            </div>
        </div>
    </div>
    <div class="border-bottom"></div>
</div>

<!-- 폴더-->
<div class="container">
    <div class="container px-4 py-2" id="featured-3">

        <!--카드-->
        <div class="row g-4 py-4 row-cols-1 row-cols-lg-4">

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>

<br>
<%-- paging --%>
<div class="container">

</div>

</body>
</html>