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
  <title>폴더 리스트</title>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/folder/list.js"></script>
  <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
  <sec:csrfMetaTags/>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>

<div class="container">
    <%--  툴바 --%>
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3">
      <!--중앙 위 타이틀-->
      <h2 class="h2">내 폴더</h2>
      <div class="btn-toolbar mt-3 mb-2 mb-md-3"> <!--mb-md- 메인 멘트 간격조정-->
        <div class="btn-group me-3"> <!--me 버튼 간격-->
          <a href="${pageContext.request.contextPath}/folder/add" class="btn btn-outline-secondary">
            <i class="bi bi-folder-plus"></i>
            폴더 생성
          </a>
        </div>
        <div class="btn-group me-2"> <!--me 버튼 간격-->
          <!--최신순 버튼-->
          <c:url value="${pageContext.request.contextPath}/folder" var="url">
            <c:param name="care_stared" value="true" />
          </c:url>
          <a href="${url}" class="btn btn btn-outline-secondary">
            <i class="bi bi-bookmark-heart"></i>
            즐겨찾기 우선
          </a>

          <c:url value="${pageContext.request.contextPath}/folder" var="url">
            <c:param name="care_stared" value="false" />
          </c:url>
          <!--최신순 버튼-->
          <a href="${url}" class="btn btn btn-outline-secondary">
            <i class="bi bi-clock"></i>
            최신순
          </a>
        </div>
      </div>
    </div>
    <div class="border-bottom">
      <p class="text-justify">${pagination.totalCount} 개의 폴더가 있습니다.</p>
    </div>
</div>

<div class="container">
  <div class="content">
    <div class="row">
      <c:forEach items="${items}" var="item">
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

<br>
<%-- paging --%>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/folder/pagination.jsp">
      <jsp:param name="previousPageExists" value="${pagination.previousPageExists}"/>
      <jsp:param name="nextPageExists" value="${pagination.nextPageExists}"/>
      <jsp:param name="startIndexNum" value="${pagination.startIndexNum}"/>
      <jsp:param name="endIndexNum" value="${pagination.endIndexNum}"/>
      <jsp:param name="currentPageNum" value="${pagination.currentPageNum}"/>
      <jsp:param name="baseUrl" value="${pageContext.request.contextPath}/folder"/>
      <jsp:param name="pageName" value="page"/>
    </jsp:include>
</div>
</body>
</html>
