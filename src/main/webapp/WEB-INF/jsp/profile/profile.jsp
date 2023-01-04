<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>${user.nickname}님의 프로필</title>
    <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
    <sec:csrfMetaTags/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/profile/profile.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bookmark/list.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bookmark/item.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/folder/list.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/folder/item.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
    <c:if test="${principal.userInternalId eq user.internalId}">
        <c:set var="baseUrl" value="${pageContext.request.contextPath}/profile/mypage"/>
    </c:if>
    <c:if test="${principal.userInternalId ne user.internalId}">
        <c:set var="baseUrl" value="${pageContext.request.contextPath}/profile/${user.internalId}"/>
    </c:if>
</sec:authorize>
<sec:authorize access="isAnonymous()">
    <c:set var="baseUrl" value="${pageContext.request.contextPath}/profile/${user.internalId}"/>
</sec:authorize>
<div class="container border-bottom" style="padding-bottom: 10px">
    <div class="row">
        <div class="col-2">
            <div style="width: 128px; height: 128px">
                <c:if test="${user.profileImage ne null && user.profileImage ne ''}">
                <img class="rounded-circle" src="${pageContext.request.contextPath}/attachment/${user.profileImage}" width="128" height="128" alt="">
                </c:if>
                <c:if test="${user.profileImage eq null || user.profileImage eq ''}">
                    <svg xmlns="http://www.w3.org/2000/svg" width="128" height="128" fill="currentColor" class="bi bi-person-circle rounded-circle" viewBox="0 0 16 16">
                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"></path>
                        <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"></path>
                    </svg>
                </c:if>
            </div>
            <div>
            <sec:authorize access="isAuthenticated()">
                <sec:authentication property="principal" var="principal"/>
                <c:if test="${principal.userInternalId eq user.internalId}">
                    <br>
                    <div>
                        <label class="btn btn-secondary" for="inputProfileImage">
                            변경
                        </label>
                        <input type="file" onchange="updateProfileImage()" id="inputProfileImage" style="display: none">
                        <c:if test="${user.profileImage ne null && user.profileImage ne ''}">
                        <button type="button" class="btn btn-danger" onclick="deleteProfileImage()">삭제</button>
                        </c:if>
                    </div>
                </c:if>
            </sec:authorize>
            </div>
        </div>
        <div class="col justify-content-start">
            <h1 class="h1">${user.nickname}</h1><br>
            <h3 class="h3 text-md-start"><i class="bi bi-bookmark-fill" style="font-size: 2rem"></i>: ${notSharedBookmarkCount + sharedBookmarkCount}개
            <sec:authorize access="isAuthenticated()">
                <sec:authentication property="principal" var="principal"/>
                <c:if test="${principal.userInternalId eq user.internalId}">
                (<i class="bi bi-people-fill" style="font-size: 2rem;"></i>: ${sharedBookmarkCount} <i class="bi bi-person" style="font-size: 2rem;"></i>: ${notSharedBookmarkCount})</h3>
                </c:if>
            </sec:authorize>
            <h3 class="h3 text-md-start"><i class="bi bi-archive-fill" style="font-size: 2rem"></i>: ${notSharedFolderCount + sharedFolderCount}개
            <sec:authorize access="isAuthenticated()">
                <sec:authentication property="principal" var="principal"/>
                <c:if test="${principal.userInternalId eq user.internalId}">
                (<i class="bi bi-people-fill" style="font-size: 2rem;"></i>: ${sharedFolderCount} <i class="bi bi-person" style="font-size: 2rem;"></i>: ${notSharedFolderCount})</h3>
                </c:if>
            </sec:authorize>
<%--            <h3 class="h3 text-md-start"><i class="bi bi-hand-thumbs-up-fill" style="font-size: 2rem"></i>: 230번</h3>--%>
        </div>
    </div>
</div>

<div class="container" style="margin-top: 10px">
    <%-- 폴더 구역 --%>
    <div class="row border-bottom" style="margin-bottom: 10px">
        <div class="row">
            <sec:authorize access="isAuthenticated()">
                <sec:authentication property="principal" var="principal"/>
                <c:if test="${principal.userInternalId eq user.internalId}">
                    <h2 class="h2">내 폴더들</h2>
                </c:if>
                <c:if test="${principal.userInternalId ne user.internalId}">
                    <h2 class="h2">공개된 폴더들</h2>
                </c:if>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <h2 class="h2">공개된 폴더들</h2>
            </sec:authorize>
            <c:if test="${folderPagination.totalCount eq 0}">
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal" var="principal"/>
                    <c:if test="${principal.userInternalId eq user.internalId}">
                        <h2 class="h2 text-center">등록된 폴더가 없습니다!</h2>
                    </c:if>
                    <c:if test="${principal.userInternalId ne user.internalId}">
                        <h2 class="h2 text-center">공개된 폴더가 없습니다!</h2>
                    </c:if>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <h2 class="h2 text-center">공개된 폴더가 없습니다!</h2>
                </sec:authorize>
            </c:if>
        </div>

        <div class="row">
            <c:forEach items="${folderItem}" var="folderItem">
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

            <c:if test="${folderPagination.totalCount ne 0}">
            <jsp:include page="/WEB-INF/jsp/folder/pagination.jsp">
                <jsp:param name="previousPageExists" value="${folderPagination.previousPageExists}"/>
                <jsp:param name="nextPageExists" value="${folderPagination.nextPageExists}"/>
                <jsp:param name="startIndexNum" value="${folderPagination.startIndexNum}"/>
                <jsp:param name="endIndexNum" value="${folderPagination.endIndexNum}"/>
                <jsp:param name="currentPageNum" value="${folderPagination.currentPageNum}"/>
                <jsp:param name="baseUrl" value="${baseUrl}"/>
                <jsp:param name="pageName" value="folder_page"/>
                <jsp:param name="anotherPageName" value="bookmark_page"/>
                <jsp:param name="anotherPageNum" value="${bookmarkPagination.currentPageNum}"/>
            </jsp:include>
            </c:if>
        </div>
    </div>

    <%-- 북마크 구역 --%>
    <div class="row">
        <div class="row">
            <sec:authorize access="isAuthenticated()">
                <sec:authentication property="principal" var="principal"/>
                <c:if test="${principal.userInternalId eq user.internalId}">
                    <h2 class="h2">내 북마크들</h2>
                </c:if>
                <c:if test="${principal.userInternalId ne user.internalId}">
                    <h2 class="h2">공개된 북마크들</h2>
                </c:if>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <h2 class="h2">공개된 북마크들</h2>
            </sec:authorize>
            <c:if test="${bookmarkPagination.totalCount eq 0}">
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal" var="principal"/>
                    <c:if test="${principal.userInternalId eq user.internalId}">
                        <h2 class="h2 text-center">등록된 북마크가 없습니다!</h2>
                    </c:if>
                    <c:if test="${principal.userInternalId ne user.internalId}">
                        <h2 class="h2 text-center">공개된 북마크가 없습니다!</h2>
                    </c:if>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <h2 class="h2 text-center">공개된 북마크가 없습니다!</h2>
                </sec:authorize>
            </c:if>
        </div>
        <div class="row">
            <c:forEach items="${bookmarkItem}" var="bookmarkItem">
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
            <jsp:include page="/WEB-INF/jsp/bookmark/pagination.jsp">
                <jsp:param name="currentPageParam" value="bookmark_page"/>
                <jsp:param name="anotherPageName" value="folder_page"/>
                <jsp:param name="anotherPageNum" value="${folderPagination.currentPageNum}"/>
                <jsp:param name="baseUrl" value="${baseUrl}"/>

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
</body>
</html>
