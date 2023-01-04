<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <c:url value="${pageContext.request.contextPath}/search" var="baseUrl">
        <c:param name="keyword" value="${keyword}"/>
    </c:url>
    <h2 class="pb-3 bi bi-search"> '${keyword}'에 대한 검색 결과입니다.</h2>
    <div class="container px-4 py-2" id="featured-1">
        <%-- 툴바 --%>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
            <!--중앙 위 타이틀-->
            <h2 class="h2 bi bi-bookmark"> 북마크 검색 결과</h2>
            <div class="btn-toolbar mt-3 mb-2 mb-md-3"> <!--mb-md- 메인 멘트 간격조정-->
                <div class="btn-group me-2"> <!--me 버튼 간격-->
                    <c:if test="${bookmarkPagination.totalCount ne 0}">
                    <sec:authorize access="isAuthenticated()">
                        <c:if test="${bookmarkCurrentUserOnly eq true}">
                            <c:url value="${baseUrl}" var="url">
                                <c:param name="folder_page" value="${folderPageNum}"/>
                                <c:param name="folder_current_user_only" value="${folderCurrentUserOnly}"/>
                                <c:param name="bookmark_page" value="1"/>
                                <c:param name="bookmark_current_user_only" value="false"/>
                            </c:url>
                            <a href="${url}" class="btn btn-secondary">
                                <i class="bi bi-person"></i>
                                내 것만
                            </a>
                        </c:if>
                        <c:if test="${bookmarkCurrentUserOnly eq false}">
                            <c:url value="${baseUrl}" var="url">
                                <c:param name="folder_page" value="${folderPageNum}"/>
                                <c:param name="folder_current_user_only" value="${folderCurrentUserOnly}"/>
                                <c:param name="bookmark_page" value="1"/>
                                <c:param name="bookmark_current_user_only" value="true"/>
                            </c:url>
                            <a href="${url}" class="btn btn-outline-secondary">
                                <i class="bi bi-person"></i>
                                내 것만
                            </a>
                        </c:if>
                    </sec:authorize>
                    </c:if>
                </div>
            </div>
        </div>

        <c:if test="${bookmarkPagination.totalCount eq 0}">
            <h2 class="h2 text-center">북마크 검색 결과가 없습니다.</h2>
        </c:if>
        <!--카드-->
        <div class="row g-4 py-4 row-cols-1 row-cols-lg-4">
            <c:forEach items="${bookmarkItems}" var="bookmarkItem">
                <div class="w-50">
                    <jsp:include page="/WEB-INF/jsp/bookmark/item.jsp">
                        <jsp:param name="id" value="${bookmarkItem.id}"/>
                        <jsp:param name="owner" value="${bookmarkItem.owner}"/>
                        <jsp:param name="title" value="${bookmarkItem.title}"/>
                        <jsp:param name="memo" value="${bookmarkItem.memo}"/>
                        <jsp:param name="url" value="${bookmarkItem.url}"/>
                        <jsp:param name="shared" value="${bookmarkItem.shared}"/>
                        <jsp:param name="stared" value="${bookmarkItem.stared}"/>
                        <jsp:param name="createdAtFormatted" value="${bookmarkItem.createdAtFormatted}"/>
                        <jsp:param name="likeCount" value="${bookmarkItem.likeCount}"/>
                        <jsp:param name="isLiked" value="${bookmarkItem.isLiked}"/>
                        <jsp:param name="authorNickname" value="${bookmarkItem.authorNickname}"/>
                        <jsp:param name="showToolbar" value="false"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
        <div class="row">
            <c:if test="${bookmarkPagination.totalCount ne 0}">
                <c:url value="${baseUrl}" var="url">
                    <c:param name="folder_current_user_only" value="${folderCurrentUserOnly}"/>
                    <c:param name="bookmark_current_user_only" value="${bookmarkCurrentUserOnly}"/>
                </c:url>

                <jsp:include page="/WEB-INF/jsp/bookmark/pagination.jsp">
                    <jsp:param name="currentPageParam" value="bookmark_page"/>
                    <jsp:param name="anotherPageName" value="folder_page"/>
                    <jsp:param name="anotherPageNum" value="${folderPagination.currentPageNum}"/>
                    <jsp:param name="baseUrl" value="${url}"/>

                    <jsp:param name="previousPageExists" value="${bookmarkPagination.previousPageExists}"/>
                    <jsp:param name="nextPageExists" value="${bookmarkPagination.nextPageExists}"/>
                    <jsp:param name="startIndexNum" value="${bookmarkPagination.startIndexNum}"/>
                    <jsp:param name="endIndexNum" value="${bookmarkPagination.endIndexNum}"/>
                    <jsp:param name="currentPageNum" value="${bookmarkPagination.currentPageNum}"/>
                    <jsp:param name="showToolbar" value="true"/>
                </jsp:include>
            </c:if>
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
                    <sec:authorize access="isAuthenticated()">
                        <c:if test="${folderCurrentUserOnly eq true}">
                            <c:url value="${baseUrl}" var="url">
                                <c:param name="folder_page" value="1"/>
                                <c:param name="folder_current_user_only" value="false"/>
                                <c:param name="bookmark_page" value="${bookmarkPageNum}"/>
                                <c:param name="bookmark_current_user_only" value="${bookmarkCurrentUserOnly}"/>
                            </c:url>
                            <a href="${url}" class="btn btn-secondary">
                                <i class="bi bi-person"></i>
                                내 것만
                            </a>
                        </c:if>
                        <c:if test="${folderCurrentUserOnly eq false}">
                            <c:url value="${baseUrl}" var="url">
                                <c:param name="folder_page" value="1"/>
                                <c:param name="folder_current_user_only" value="true"/>
                                <c:param name="bookmark_page" value="${bookmarkPageNum}"/>
                                <c:param name="bookmark_current_user_only" value="${bookmarkCurrentUserOnly}"/>
                            </c:url>
                            <a href="${url}" class="btn btn-outline-secondary">
                                <i class="bi bi-person"></i>
                                내 것만
                            </a>
                        </c:if>
                    </sec:authorize>
                </div>
            </div>
        </div>

        <c:if test="${folderPagination.totalCount eq 0}">
            <h2 class="h2 text-center">폴더 검색 결과가 없습니다.</h2>
        </c:if>
        <!--카드-->
        <div class="row g-4 py-4 row-cols-1 row-cols-lg-4">
            <c:forEach items="${folderItems}" var="folderItem">
                <jsp:include page="/WEB-INF/jsp/folder/item.jsp">
                    <jsp:param name="id" value="${folderItem.id}"/>
                    <jsp:param name="owner" value="${folderItem.owner}"/>
                    <jsp:param name="thumbnail" value="${folderItem.thumbnail}"/>
                    <jsp:param name="title" value="${folderItem.title}"/>
                    <jsp:param name="memo" value="${folderItem.memo}"/>
                    <jsp:param name="itemCount" value="${folderItem.itemCount}"/>
                    <jsp:param name="stared" value="${folderItem.stared}"/>
                    <jsp:param name="shared" value="${folderItem.shared}"/>
                    <jsp:param name="isLiked" value="${folderItem.isLiked}"/>
                    <jsp:param name="likeCount" value="${folderItem.likeCount}"/>
                    <jsp:param name="showToolbar" value="false"/>
                </jsp:include>
            </c:forEach>
        </div>

        <div class="row">
            <c:if test="${folderPagination.totalCount ne 0}">
                <jsp:include page="/WEB-INF/jsp/folder/pagination.jsp">
                    <jsp:param name="previousPageExists" value="${folderPagination.previousPageExists}"/>
                    <jsp:param name="nextPageExists" value="${folderPagination.nextPageExists}"/>
                    <jsp:param name="startIndexNum" value="${folderPagination.startIndexNum}"/>
                    <jsp:param name="endIndexNum" value="${folderPagination.endIndexNum}"/>
                    <jsp:param name="currentPageNum" value="${folderPagination.currentPageNum}"/>
                    <jsp:param name="baseUrl" value="${baseUrl}"/>
                    <jsp:param name="pageName" value="folder_page"/>
                </jsp:include>
            </c:if>
        </div>

    </div>
</div>

</body>
</html>