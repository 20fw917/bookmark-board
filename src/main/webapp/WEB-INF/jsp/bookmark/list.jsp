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
        <a href="${pageContext.request.contextPath}/bookmark/add" class="btn btn-primary">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmark-plus" viewBox="0 0 16 16">
                <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
            </svg>
            북마크 추가
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
                <div class="card w-50">
                    <div class="card-body">
                        <h5 class="card-title">${item.title}</h5>
                        <p class="card-text">${item.memo}</p>
                        <p class="card-text">${item.url}</p>
                        <a href="${item.url}" class="btn btn-primary">
                            <i class="bi bi-globe"></i>
                            방문
                        </a>
                        <div class="text-end">
                            <button type="button" class="btn btn-sm">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                                </svg>
                            </button>
                            <button class="btn btn-secondary">
                                <i class="bi bi-pencil"></i>
                                편집
                            </button>
                            <a href="${pageContext.request.contextPath}/" class="btn btn-danger">
                                <i class="bi bi-trash3"></i>
                                삭제
                            </a>
                            <br>
                            <p class="fst-italic">${item.createdAtFormatted}에 추가 됨.</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
<br>
    <div class="container">
        <nav>
            <ul class="pagination justify-content-center">
                <c:if test="${staredBookmarkPagination.previousPageExists}">
                <li class="page-item">
                    <c:url value="${pageContext.request.contextPath}/bookmark" var="url">
                        <c:param name="not_stared_page" value="${notStaredBookmarkPagination.currentPageNum}" />
                        <c:param name="stared_page" value="${staredBookmarkPagination.startIndexNum - 10}" />
                    </c:url>

                    <a class="page-link" href="${url}" aria-label="Previous">
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
                    <c:url value="${pageContext.request.contextPath}/bookmark" var="url">
                        <c:param name="not_stared_page" value="${notStaredBookmarkPagination.currentPageNum}" />
                        <c:param name="stared_page" value="${status.current}" />
                    </c:url>

                    <li class="page-item"><a class="page-link" href="${url}">${status.current}</a></li>
                </c:otherwise>
                </c:choose>
                </c:forEach>

                <c:if test="${staredBookmarkPagination.nextPageExists}">
                    <c:url value="${pageContext.request.contextPath}/bookmark" var="url">
                        <c:param name="not_stared_page" value="${notStaredBookmarkPagination.currentPageNum}" />
                        <c:param name="stared_page" value="${staredBookmarkPagination.startIndexNum + 10}" />
                    </c:url>

                <li class="page-item">
                    <a class="page-link" href="${url}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                </c:if>
            </ul>
        </nav>
    </div>
<br>
    </c:if>


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
            <div class="card w-50">
                <div class="card-body">
                    <h5 class="card-title">${item.title}</h5>
                    <p class="card-text">${item.memo}</p>
                    <p class="card-text">${item.url}</p>
                    <a href="${item.url}" class="btn btn-primary">
                        <i class="bi bi-globe"></i>
                        방문
                    </a>
                    <div class="text-end">
                        <button type="button" class="btn btn-sm">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star" viewBox="0 0 16 16">
                                <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                            </svg>
                        </button>
                        <button class="btn btn-secondary">
                            <i class="bi bi-pencil"></i>
                            편집
                        </button>
                        <a href="${pageContext.request.contextPath}/" class="btn btn-danger">
                            <i class="bi bi-trash3"></i>
                            삭제
                        </a>
                        <br>
                        <p class="fst-italic">${item.createdAtFormatted}에 추가 됨.</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <br>
    <div class="container">
        <nav>
            <ul class="pagination justify-content-center">
                <c:if test="${notStaredBookmarkPagination.previousPageExists}">
                    <c:url value="${pageContext.request.contextPath}/bookmark" var="url">
                        <c:param name="not_stared_page" value="${notStaredBookmarkItems.startIndexNum - 10}" />
                        <c:param name="stared_page" value="${staredBookmarkPagination.currentPageNum}" />
                    </c:url>

                    <li class="page-item">
                        <a class="page-link" href="${url}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <c:forEach begin="${notStaredBookmarkPagination.startIndexNum}" end="${notStaredBookmarkPagination.endIndexNum}" step="1" varStatus="status">
                    <c:choose>
                        <c:when test="${status.current eq notStaredBookmarkPagination.currentPageNum}">
                            <li class="page-item active" aria-current="page">
                                <span class="page-link">${status.current}</span>
                            </li>
                        </c:when>

                        <c:otherwise>
                            <c:url value="${pageContext.request.contextPath}/bookmark" var="url">
                                <c:param name="not_stared_page" value="${status.current}" />
                                <c:param name="stared_page" value="${staredBookmarkPagination.currentPageNum}" />
                            </c:url>

                            <li class="page-item"><a class="page-link" href="${url}">${status.current}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${staredBookmarkPagination.nextPageExists}">
                    <li class="page-item">
                        <c:url value="${pageContext.request.contextPath}/bookmark" var="url">
                            <c:param name="not_stared_page" value="${notStaredBookmarkItems.startIndexNum + 10}" />
                            <c:param name="stared_page" value="${staredBookmarkPagination.currentPageNum}" />
                        </c:url>

                        <a class="page-link" href="${url}" aria-label="Next">
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