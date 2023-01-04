<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>통합 검색</title>
    <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
    <sec:csrfMetaTags/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bookmark/list.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bookmark/item.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/folder/list.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/folder/item.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container">
    <h2 class="pb-3 bi bi-search"> '${keyword}'에 대한 검색 결과입니다.</h2>
    <div class="container px-4 py-2" id="featured-1">
        <%-- 툴바 --%>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
            <!--중앙 위 타이틀-->
            <h2 class="h2 bi bi-bookmark"> 북마크 검색 결과</h2>
            <div class="btn-toolbar mt-3 mb-2 mb-md-3"> <!--mb-md- 메인 멘트 간격조정-->
                <div class="btn-group me-2"> <!--me 버튼 간격-->
                    <a href="#" class="btn btn btn-outline-secondary">
                        <i class="bi bi-person"></i>
                        내 것만
                    </a>
                </div>
            </div>
        </div>

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
    <div class="container px-4 py-2" id="featured-2">
        <%-- 툴바 --%>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
            <!--중앙 위 타이틀-->
            <h2 class="h2 bi bi-folder"> 폴더 검색 결과</h2>
            <div class="btn-toolbar mt-3 mb-2 mb-md-3"> <!--mb-md- 메인 멘트 간격조정-->
                <div class="btn-group me-2"> <!--me 버튼 간격-->
                    <a href="#" class="btn btn btn-outline-secondary">
                        <i class="bi bi-person"></i>
                        내 것만
                    </a>
                </div>
            </div>
        </div>

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

</body>
</html>