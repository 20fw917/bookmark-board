<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.104.2">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/dashboard/">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/folder/detail.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/folder/item.js"></script>
    <title>'${folder.title}' 폴더 상세</title>
    <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
    <sec:csrfMetaTags/>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>

<div class="container">
    <br>
    <!--왼쪽 파트-->
    <div class="row">
        <div class="col-5">
            <div class="card p-4" style="width: 30rem; height: 46rem; float: right; margin-right: 1rem;">
                <c:if test="${folder.thumbnail ne null && folder.thumbnail ne ''}">
                    <img src="${pageContext.request.contextPath}/attachment/${folder.thumbnail}" class="card-img-top detail-pic" alt="">
                </c:if>
                <c:if test="${folder.thumbnail eq null || folder.thumbnail eq ''}">
                    <div class="card-img-top detail-pic">
                        <svg xmlns="http://www.w3.org/2000/svg" width="238" height="238" fill="currentColor" class="bi bi-archive" viewBox="0 0 16 16">
                            <path d="M0 2a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v2a1 1 0 0 1-1 1v7.5a2.5 2.5 0 0 1-2.5 2.5h-9A2.5 2.5 0 0 1 1 12.5V5a1 1 0 0 1-1-1V2zm2 3v7.5A1.5 1.5 0 0 0 3.5 14h9a1.5 1.5 0 0 0 1.5-1.5V5H2zm13-3H1v2h14V2zM5 7.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5z"></path>
                        </svg>
                    </div>
                </c:if>
                <div class="card-body">
                    <h5 class="card-title detail-card">${folder.title}
                        <c:if test="${folder.shared eq true}">
                            <i class="bi bi-people-fill" style="font-size: 20px;"></i>
                        </c:if>
                        <c:if test="${folder.shared eq false}">
                            <i class="bi bi-person" style="font-size: 20px;"></i>
                        </c:if></h5>
                    <c:if test="${folder.shared}">
                        <a href="${pageContext.request.contextPath}/profile/${folder.owner}" style="text-decoration: none; color:black;">
                            <p class="card-text detail-card">생성자: ${folder.authorNickname}님
                        </a>
                        <br><p class="card-text detail-card"><i class="bi bi-hand-thumbs-up-fill"></i>: ${folder.likeCount}회</p>
                    </c:if>

                    <p class="card-text detail-card">${folder.memo}</p>
                    <p class="card-text detail-card">${folder.itemCount}개의 북마크가 있습니다.</p>

                    <div class="text-end">
                        <!--편집, 삭제 버튼-->
                        <sec:authorize access="isAuthenticated()">
                            <sec:authentication property="principal" var="principal"/>
                            <c:if test="${principal.userInternalId eq folder.owner}">
                                <!--즐겨찾기 Button-->
                                <c:if test="${folder.stared eq true}">
                                    <button type="button" onclick="updateStared(${folder.id}, false)" class="btn btn-sm">
                                        <i class="bi-star-fill" style="font-size:20px; color: #FDD017; cursor: pointer;"></i>
                                    </button>
                                </c:if>
                                <c:if test="${folder.stared eq false}">
                                    <button type="button" onclick="updateStared(${folder.id}, true)" class="btn btn-sm">
                                        <i class="bi-star" style="font-size:20px; color: #FDD017; cursor: pointer;"></i>
                                    </button>
                                </c:if>

                                <c:if test="${folder.shared eq true}">
                                    <button type="button" class="btn btn-sm card-title detail-card" onclick="updateShared(${folder.id}, false)">
                                        <i class="bi bi-people-fill" style="font-size: 20px;"></i>
                                    </button>
                                </c:if>
                                <c:if test="${folder.shared eq false}">
                                    <button type="button" class="btn btn-sm card-title detail-card" onclick="updateShared(${folder.id}, true)">
                                        <i class="bi bi-person" style="font-size: 20px;"></i>
                                    </button>
                                </c:if>

                                <c:url value="${pageContext.request.contextPath}/folder/update" var="url">
                                    <c:param name="id" value="${folder.id}" />
                                </c:url>
                                <a href="${url}" class="btn btn-secondary">
                                    <i class="bi bi-pencil"></i>
                                    편집
                                </a>
                                <c:url value="${pageContext.request.contextPath}/folder/update" var="url">
                                    <c:param name="id" value="${folder.id}" />
                                </c:url>
                                <button onclick="deleteFolderAtDetail(${folder.id})" class="btn btn-danger text-end">
                                    <i class="bi bi-trash"></i>
                                    삭제
                                </button>
                                <br>
                            </c:if>
                            <c:if test="${principal.userInternalId ne folder.owner}">
                                <button type="button" onclick="copyFolder(${folder.id})" class="btn btn-sm btn-secondary">
                                    <i class="bi bi-clipboard-plus"></i>
                                </button>
                                <c:if test="${folder.isLiked eq false}">
                                    <button type="button" onclick="likeFolder(${folder.id}, true)" class="btn btn-sm btn-secondary">
                                        <i class="bi bi-hand-thumbs-up"></i>
                                    </button>
                                </c:if>
                                <c:if test="${folder.isLiked eq true}">
                                    <button type="button" onclick="likeFolder(${folder.id}, false)" class="btn btn-sm btn-secondary">
                                        <i class="bi bi-hand-thumbs-up-fill"></i>
                                    </button>
                                </c:if>
                            </c:if>
                        </sec:authorize>
                        <p class="fst-italic">${folder.createdAtFormatted}에 생성 됨</p>
                    </div>
                </div>
            </div>
        </div>

        <!--오른쪽 파트-->
        <div class="col-5">
            <c:if test="${folder.itemCount eq 0}">
                <h2 class="h2 text-center">등록된 북마크가 없어요!</h2>
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal" var="principal"/>
                    <c:if test="${principal.userInternalId eq folder.owner}">
                        <br><p class="text-md-center">편집 버튼을 눌러서 북마크를 등록하세요!</p>
                    </c:if>
                </sec:authorize>
            </c:if>
            <div class="row">
                <!--북마크 카드-->
                <c:forEach items="${bookmarkList}" var="item">
                    <jsp:include page="/WEB-INF/jsp/bookmark/item.jsp">
                        <jsp:param name="id" value="${item.id}"/>
                        <jsp:param name="title" value="${item.title}"/>
                        <jsp:param name="memo" value="${item.memo}"/>
                        <jsp:param name="url" value="${item.url}"/>
                        <jsp:param name="shared" value="${item.shared}"/>
                        <jsp:param name="stared" value="${item.stared}"/>
                        <jsp:param name="createdAtFormatted" value="${item.createdAtFormatted}"/>
                        <jsp:param name="showToolbar" value="false"/>
                        <jsp:param name="pageName" value="page"/>
                    </jsp:include>
                </c:forEach>
            </div>
        </div>

        <div class="row">
            <c:if test="${folder.itemCount ne 0}">
                <jsp:include page="/WEB-INF/jsp/folder/pagination.jsp">
                    <jsp:param name="previousPageExists" value="${pagination.previousPageExists}"/>
                    <jsp:param name="nextPageExists" value="${pagination.nextPageExists}"/>
                    <jsp:param name="startIndexNum" value="${pagination.startIndexNum}"/>
                    <jsp:param name="endIndexNum" value="${pagination.endIndexNum}"/>
                    <jsp:param name="currentPageNum" value="${pagination.currentPageNum}"/>
                    <jsp:param name="baseUrl" value="${pageContext.request.contextPath}/folder/detail/${folder.id}"/>
                </jsp:include>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>

