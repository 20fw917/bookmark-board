<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h1>내 북마크 리스트</h1>
    <br>
    <p class="text-justify">총 ${staredBookmarkPagination.totalCount + notStaredBookmarkPagination.totalCount} 개의 저장된 북마크가 있습니다.</p>
    <div class="container text-end">
        <a href="${pageContext.request.contextPath}/bookmark/add">
            <button class="btn btn-primary">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmark-plus" viewBox="0 0 16 16">
                    <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                    <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                </svg>
                북마크 추가
            </button>
        </a>
    </div>
    <br>
</div>

<%-- 즐겨찾기한 북마크 Area --%>
<div class="container">
    <c:if test="${staredBookmarkPagination.totalCount ne 0}">
    <h3>즐겨찾는 북마크</h3>
    <br>
    <div class="row">
        <c:forEach items="${staredBookmarkItems}" var="item">
            <jsp:include page="bookmark_item.jsp"/>
        </c:forEach>
    </div>

    <div class="container">
        <nav>
            <ul class="pagination justify-content-center">
                <c:if test="${staredBookmarkPagination.previousPageExists}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/bookmark?page=${staredBookmarkPagination.startIndexNum - 10}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                </c:if>

                <c:forEach begin="${staredBookmarkPagination.startIndexNum}" end="${staredBookmarkPagination.endIndexNum}" step="1" varStatus="status">
                <c:choose>
                <c:when test="${status.current eq staredBookmarkPagination.currentPageNum}">
                <li class="page-item active" aria-current="page">
                    <span class="page-link">${status.current}</span>
                </li>
                </c:when>

                <c:otherwise>
                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/bookmark?page=${status.current}">${status.current}</a></li>
                </c:otherwise>
                </c:choose>
                </c:forEach>

                <c:if test="${staredBookmarkPagination.nextPageExists}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/bookmark?page=${pagination.startIndexNum + 10}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                </c:if>
            </ul>
        </nav>
    </div>
    </c:if>
</div>
<br>

<%-- 일반 북마크 Area --%>
<div class="container">
    <c:if test="${notStaredBookmarkPagination.totalCount eq 0}">
        <h3 class="text-center">일반 북마크가 없습니다.</h3>
        <p class="text-justify text-center">모든 북마크가 즐겨찾기 되어있거나 등록한 북마크가 없습니다. 북마크를 추가해보세요!</p>
    </c:if>

    <c:if test="${notStaredBookmarkPagination.totalCount ne 0}">
    <h3>일반 북마크</h3><br>
    <div class="row">
        <c:forEach items="${notStaredBookmarkItems}" var="item">
            <jsp:include page="bookmark_item.jsp"/>
        </c:forEach>
    </div>

    <div class="container">
        <nav>
            <ul class="pagination justify-content-center">
                <c:if test="${notStaredBookmarkItems.previousPageExists}">
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/bookmark?page=${notStaredBookmarkItems.startIndexNum - 10}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <c:forEach begin="${notStaredBookmarkItems.startIndexNum}" end="${notStaredBookmarkItems.endIndexNum}" step="1" varStatus="status">
                    <c:choose>
                        <c:when test="${status.current eq notStaredBookmarkItems.currentPageNum}">
                            <li class="page-item active" aria-current="page">
                                <span class="page-link">${status.current}</span>
                            </li>
                        </c:when>

                        <c:otherwise>
                            <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/bookmark?page=${status.current}">${status.current}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${staredBookmarkPagination.nextPageExists}">
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/bookmark?page=${notStaredBookmarkItems.startIndexNum + 10}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
    </c:if>
</div>
<br>

</body>
</html>