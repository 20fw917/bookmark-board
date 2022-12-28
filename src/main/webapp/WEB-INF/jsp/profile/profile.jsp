<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>${item.nickname}님의 프로필</title>
    <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
    <sec:csrfMetaTags/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/profile/profile.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="container border-bottom" style="padding-bottom: 10px">
    <div class="row">
        <div class="col-2">
            <div style="width: 128px; height: 128px">
                <c:if test="${item.profileImage eq null || item.profileImage eq ''}">
                    <div class="rounded-circle">
                        <svg xmlns="http://www.w3.org/2000/svg" width="128" height="128" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                            <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                        </svg>
                    </div>
                </c:if>
                <c:if test="${item.profileImage ne null && item.profileImage ne ''}">
                    <img class="rounded-circle" src="${pageContext.request.contextPath}/attachment/${item.profileImage}" width="128" height="128" alt="">
                </c:if>
            </div>
            <div>
                <c:if test="${currentUserInternalId eq item.internalId}">
                    <br>
                    <div>
                        <label class="btn btn-secondary" for="inputProfileImage">
                            변경
                        </label>
                        <input type="file" onchange="updateProfileImage()" id="inputProfileImage" style="display: none">
                        <button type="button" class="btn btn-danger" onclick="deleteProfileImage()">삭제</button>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col justify-content-start">
            <h1 class="h1">${item.nickname}</h1><br>
            <h3 class="h3 text-md-start"><i class="bi bi-bookmark-fill" style="font-size: 2rem"></i>: 5개(<i class="bi bi-people-fill" style="font-size: 2rem;"></i>: 2 <i class="bi bi-person" style="font-size: 2rem;"></i>: 3)</h3>
            <h3 class="h3 text-md-start"><i class="bi bi-archive-fill" style="font-size: 2rem"></i>: 5개(<i class="bi bi-people-fill" style="font-size: 2rem;"></i>: 2 <i class="bi bi-person" style="font-size: 2rem;"></i>: 3)</h3>
            <h3 class="h3 text-md-start"><i class="bi bi-hand-thumbs-up-fill" style="font-size: 2rem"></i>: 230번</h3>
        </div>
    </div>
</div>

<div class="container">

</div>
</body>
</html>
