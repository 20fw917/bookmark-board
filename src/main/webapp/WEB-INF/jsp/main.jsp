<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>북마크 시스템</title>
    <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container">
    <div class="container px-4 py-2" id="featured-3">
        <h2 class="pb-3  bi bi-bookmark-star border-bottom"> 즐겨찾는 폴더</h2>

        <!--카드-->
        <div class="row g-4 py-4 row-cols-1 row-cols-lg-4">

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top" >
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top" >
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top" >
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top" >
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

        </div>

        <!--더보기 버튼-->
        <div class="d-grid gap-1 d-md-flex justify-content-md-end">
            <button type="button" class="bi bi-search btn btn-outline-primary btn-sm">&nbsp;더보기</button>
        </div>

    </div>
</div>

<div class="container">
    <div class="container px-4 py-2" id="featured-3">
        <h2 class="pb-3  bi bi-hand-thumbs-up border-bottom"> 추천 폴더</h2>

        <!--카드-->
        <div class="row g-4 py-4 row-cols-1 row-cols-lg-4">

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top" >
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top" >
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top" >
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

            <div class="feature col">
                <div class="card p-3" style="width: 18rem; float: left;">
                    <img src="..." class="card-img-top" >
                    <div class="card-body">
                        <h5 class="card-title">제목</h5>
                        <p class="card-text">내용내용</p>
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            </div>

        </div>

        <!--더보기 버튼-->
        <div class="d-grid gap-1 d-md-flex justify-content-md-end">
            <button type="button" class="bi bi-search btn btn-outline-primary btn-sm">&nbsp;더보기</button>
        </div>

    </div>
</div>

<br>
<br>

</body>
</html>