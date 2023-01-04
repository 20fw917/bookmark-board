<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<div class="container d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3">
    <h2 style="width: 50%" class="h2 bi bi-bookmark"> 내 북마크</h2>
    <div class="container text-end">
        <a href="${pageContext.request.contextPath}/bookmark/add" class="btn btn-outline-secondary">
            <i class="bi bi-bookmark-plus"></i>
            북마크 추가
        </a>
    </div>
    <br>
</div>
<div class="container border-bottom">
    <p class="text-justify">총 ${staredBookmarkPagination.totalCount + notStaredBookmarkPagination.totalCount} 개의 저장된
        북마크가 있습니다.</p>
</div>

<%-- 즐겨찾기한 북마크 Area --%>
<div class="container" style="margin-top: 20px">
    <c:if test="${staredBookmarkPagination.totalCount ne 0}">
    <h3 class="bi bi-bookmark-star"> 즐겨찾는 북마크</h3>
    <br>
    <div class="row">
        <c:forEach items="${staredBookmarkItems}" var="item">
            <div class="w-50">
                <jsp:include page="/WEB-INF/jsp/bookmark/item.jsp">
                    <jsp:param name="id" value="${item.id}"/>
                    <jsp:param name="owner" value="${item.owner}"/>
                    <jsp:param name="title" value="${item.title}"/>
                    <jsp:param name="memo" value="${item.memo}"/>
                    <jsp:param name="url" value="${item.url}"/>
                    <jsp:param name="shared" value="${item.shared}"/>
                    <jsp:param name="stared" value="${item.stared}"/>
                    <jsp:param name="createdAtFormatted" value="${item.createdAtFormatted}"/>
                    <jsp:param name="likeCount" value="${item.likeCount}"/>
                    <jsp:param name="isLiked" value="${item.isLiked}"/>
                    <jsp:param name="authorNickname" value="${item.authorNickname}"/>
                    <jsp:param name="showToolbar" value="true"/>
                    <jsp:param name="baseUrl" value="${pageContext.request.contextPath}/bookmark"/>
                </jsp:include>
            </div>
        </c:forEach>
    </div>
</div>
<br>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/bookmark/pagination.jsp">
        <jsp:param name="currentPageParam" value="stared_page"/>
        <jsp:param name="anotherPageName" value="not_stared_page"/>
        <jsp:param name="anotherPageNum" value="${notStaredBookmarkPagination.currentPageNum}"/>

        <jsp:param name="previousPageExists" value="${staredBookmarkPagination.previousPageExists}"/>
        <jsp:param name="nextPageExists" value="${staredBookmarkPagination.nextPageExists}"/>
        <jsp:param name="startIndexNum" value="${staredBookmarkPagination.startIndexNum}"/>
        <jsp:param name="endIndexNum" value="${staredBookmarkPagination.endIndexNum}"/>
        <jsp:param name="currentPageNum" value="${staredBookmarkPagination.currentPageNum}"/>
    </jsp:include>
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
        <h3 class="bi bi-bookmark"> 일반 북마크</h3><br>
        <div class="row">
            <c:forEach items="${notStaredBookmarkItems}" var="item">
                <div class="w-50">
                    <jsp:include page="/WEB-INF/jsp/bookmark/item.jsp">
                        <jsp:param name="id" value="${item.id}"/>
                        <jsp:param name="owner" value="${item.owner}"/>
                        <jsp:param name="title" value="${item.title}"/>
                        <jsp:param name="memo" value="${item.memo}"/>
                        <jsp:param name="url" value="${item.url}"/>
                        <jsp:param name="shared" value="${item.shared}"/>
                        <jsp:param name="stared" value="${item.stared}"/>
                        <jsp:param name="createdAtFormatted" value="${item.createdAtFormatted}"/>
                        <jsp:param name="likeCount" value="${item.likeCount}"/>
                        <jsp:param name="isLiked" value="${item.isLiked}"/>
                        <jsp:param name="authorNickname" value="${item.authorNickname}"/>
                        <jsp:param name="createdAtFormatted" value="${item.createdAtFormatted}"/>
                        <jsp:param name="showToolbar" value="true"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>

        <br>
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/bookmark/pagination.jsp">
                <jsp:param name="currentPageParam" value="not_stared_page"/>
                <jsp:param name="anotherPageName" value="stared_page"/>
                <jsp:param name="anotherPageNum" value="${staredBookmarkPagination.currentPageNum}"/>

                <jsp:param name="previousPageExists" value="${notStaredBookmarkPagination.previousPageExists}"/>
                <jsp:param name="nextPageExists" value="${notStaredBookmarkPagination.nextPageExists}"/>
                <jsp:param name="startIndexNum" value="${notStaredBookmarkPagination.startIndexNum}"/>
                <jsp:param name="endIndexNum" value="${notStaredBookmarkPagination.endIndexNum}"/>
                <jsp:param name="currentPageNum" value="${notStaredBookmarkPagination.currentPageNum}"/>
                <jsp:param name="showToolbar" value="true"/>
            </jsp:include>
        </div>
    </c:if>
</div>
<br>

</body>
</html>