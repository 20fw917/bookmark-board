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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bookmark/list.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container">
    <h1>내 북마크 리스트</h1>
    <br>
    <p class="text-justify">총 ${staredBookmarkPagination.totalCount + notStaredBookmarkPagination.totalCount} 개의 저장된 북마크가 있습니다.</p>
    <div class="container text-end border-bottom pt-3 pb-2 mb-3">
        <a href="${pageContext.request.contextPath}/bookmark/add" class="btn btn-primary">
            <i class="bi bi-bookmark-plus"></i>
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
                            <button type="button" class="btn btn-sm" onclick="updateStared(${item.id}, false)">
                                <i class="bi bi-star-fill"></i>
                            </button>
                            <c:url value="${pageContext.request.contextPath}/bookmark/update" var="url">
                                <c:param name="id" value="${item.id}" />
                            </c:url>
                            <a href="${url}" class="btn btn-secondary">
                                <i class="bi bi-pencil"></i>
                                편집
                            </a>
                            <button onclick="deleteBookmark(${item.id})" class="btn btn-danger">
                                <i class="bi bi-trash"></i>
                                삭제
                            </button>
                            <br>
                            <p class="fst-italic">${item.createdAtFormatted}에 추가 됨</p>
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
                        <button type="button" class="btn btn-sm" onclick="updateStared(${item.id}, true)">
                            <i class="bi bi-star"></i>
                        </button>
                        <c:url value="${pageContext.request.contextPath}/bookmark/update" var="url">
                            <c:param name="id" value="${item.id}" />
                        </c:url>
                        <a href="${url}" class="btn btn-secondary">
                            <i class="bi bi-pencil"></i>
                            편집
                        </a>
                        <button onclick="deleteBookmark(${item.id})" class="btn btn-danger">
                            <i class="bi bi-trash"></i>
                            삭제
                        </button>
                        <br>
                        <p class="fst-italic">${item.createdAtFormatted}에 추가 됨</p>
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