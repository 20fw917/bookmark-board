<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>북마크 시스템</title>
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
    <sec:authorize access="isAuthenticated()">
        <c:if test="${myFolderPagination.totalCount ne 0}">
        <div class="container px-4 py-2" id="featured-1">
            <h2 class="pb-3  bi bi-bookmark-star border-bottom"> 즐겨찾는 폴더</h2>

            <!--카드-->
            <div class="row g-4 py-4 row-cols-1 row-cols-lg-4">
                <div class="content">
                    <c:forEach items="${myFolderItems}" var="item">
                        <jsp:include page="/WEB-INF/jsp/folder/item.jsp">
                            <jsp:param name="id" value="${item.id}"/>
                            <jsp:param name="thumbnail" value="${item.thumbnail}"/>
                            <jsp:param name="title" value="${item.title}"/>
                            <jsp:param name="memo" value="${item.memo}"/>
                            <jsp:param name="itemCount" value="${item.itemCount}"/>
                            <jsp:param name="stared" value="${item.stared}"/>
                            <jsp:param name="shared" value="${item.shared}"/>
                            <jsp:param name="showToolbar" value="true"/>
                        </jsp:include>
                    </c:forEach>
                </div>
            </div>

            <%-- paging --%>
            <div class="container">
                <jsp:include page="/WEB-INF/jsp/folder/pagination.jsp">
                    <jsp:param name="previousPageExists" value="${myFolderPagination.previousPageExists}"/>
                    <jsp:param name="nextPageExists" value="${myFolderPagination.nextPageExists}"/>
                    <jsp:param name="startIndexNum" value="${myFolderPagination.startIndexNum}"/>
                    <jsp:param name="endIndexNum" value="${myFolderPagination.endIndexNum}"/>
                    <jsp:param name="currentPageNum" value="${myFolderPagination.currentPageNum}"/>
                    <jsp:param name="baseUrl" value="${pageContext.request.contextPath}/folder"/>
                    <jsp:param name="pageName" value="page"/>
                </jsp:include>
            </div>
        </div>
        </c:if>
    </sec:authorize>
</div>

<div class="container">
    <div class="container px-4 py-2" id="featured-2">
        <c:if test="${suggestFolderPagination.totalCount ne 0}">
        <h2 class="pb-3 bi bi-hand-thumbs-up border-bottom">추천 폴더</h2>

        <!--카드-->
        <div class="row g-4 py-4 row-cols-1">
            <div class="content">
                <c:forEach items="${suggestFolderItems}" var="item">
                    <jsp:include page="/WEB-INF/jsp/folder/item.jsp">
                        <jsp:param name="id" value="${item.id}"/>
                        <jsp:param name="thumbnail" value="${item.thumbnail}"/>
                        <jsp:param name="title" value="${item.title}"/>
                        <jsp:param name="memo" value="${item.memo}"/>
                        <jsp:param name="itemCount" value="${item.itemCount}"/>
                        <jsp:param name="stared" value="${item.stared}"/>
                        <jsp:param name="shared" value="${item.shared}"/>
                        <jsp:param name="showToolbar" value="false"/>
                    </jsp:include>
                </c:forEach>
            </div>
        </div>

        <%-- paging --%>
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/folder/pagination.jsp">
                <jsp:param name="previousPageExists" value="${suggestFolderPagination.previousPageExists}"/>
                <jsp:param name="nextPageExists" value="${suggestFolderPagination.nextPageExists}"/>
                <jsp:param name="startIndexNum" value="${suggestFolderPagination.startIndexNum}"/>
                <jsp:param name="endIndexNum" value="${suggestFolderPagination.endIndexNum}"/>
                <jsp:param name="currentPageNum" value="${suggestFolderPagination.currentPageNum}"/>
                <jsp:param name="baseUrl" value="${pageContext.request.contextPath}/folder"/>
                <jsp:param name="pageName" value="page"/>
            </jsp:include>
        </div>
    </div>
    </c:if>
</div>
<br>
<br>
</body>
</html>