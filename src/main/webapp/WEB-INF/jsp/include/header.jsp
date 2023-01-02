<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="URL" value="${pageContext.request.servletPath}"/>

<header class="p-3 text-bg-dark" style="margin-bottom: 40px">
  <div class="container">
    <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
      <a style="margin-right: 13px; font-size: 22px;" href="${pageContext.request.contextPath}/" class="d-flex align-items-center mb-2 mb-lg-0  bi bi-journal-bookmark-fill text-white text-decoration-none">
        &nbsp;북마크
      </a>

      <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
        <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 bi bi-house
        <c:choose>
          <c:when test="${URL eq '/WEB-INF/jsp/main.jsp'}">
               link-secondary
          </c:when>
          <c:otherwise>
               text-white
          </c:otherwise>
      </c:choose>"> 홈</a></li>
        <li><a href="${pageContext.request.contextPath}/bookmark" class="nav-link px-2 bi bi-bookmark <c:choose>
          <c:when test="${URL eq '/WEB-INF/jsp/bookmark/list.jsp'}">
               link-secondary
          </c:when>
          <c:otherwise>
               text-white
          </c:otherwise>
      </c:choose>"> 내 북마크</a></li>
        <li><a href="${pageContext.request.contextPath}/folder" class="nav-link px-2 bi bi-folder <c:choose>
          <c:when test="${URL eq '/WEB-INF/jsp/folder/list.jsp'}">
               link-secondary
          </c:when>
          <c:otherwise>
               text-white
          </c:otherwise>
      </c:choose>"> 내 폴더</a></li>
      </ul>

      <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
        <input type="search" class="form-control form-control-dark text-bg-dark" placeholder="검색" aria-label="Search">
      </form>
    <sec:authorize access="isAuthenticated()">
        <div class="dropdown text-end">
          <a href="#" class="d-block text-white text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
            <sec:authentication property="principal" var="principal"/>
            <c:if test="${principal.profileImage ne null && principal.profileImage ne ''}">
              <img class="rounded-circle" src="${pageContext.request.contextPath}/attachment/${principal.profileImage}" width="32" height="32" alt="">
            </c:if>
            <c:if test="${principal.profileImage eq null || principal.profileImage eq ''}">
              <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-person-circle rounded-circle" viewBox="0 0 16 16">
                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"></path>
                <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"></path>
              </svg>
            </c:if>
          </a>
          <ul class="dropdown-menu text-small">
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile/mypage">마이 페이지</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile/update">개인정보 수정</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">로그아웃</a></li>
          </ul>
        </div>
      </div>
    </sec:authorize>

      <sec:authorize access="isAnonymous()">
        <c:if test="${URL ne '/WEB-INF/jsp/login.jsp'}">
          <div class="text-end">
            <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-light me-2">로그인</a>
            <a href="${pageContext.request.contextPath}/user/register" class="btn btn-warning">회원가입</a>
          </div>
        </c:if>
      </sec:authorize>
    </div>
</header>
