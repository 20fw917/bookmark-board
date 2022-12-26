<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <!--카드-->
        <div class="card p-3" style="width: 17rem; float: left; margin: 20px;">
          <img src="https://cdn.britannica.com/82/152982-050-11159CF4/Daniel-Radcliffe-Rupert-Grint-Emma-Watson-Harry.jpg" class="card-img-top" alt="">
          <div class="card-body">
            <h5 class="card-title">${item.title}</h5>
            <p class="card-text">${item.memo}</p>
            <p class="card-text text-end">${item.itemCount}개의 북마크가 있습니다.</p>
            <!--자세히 보기 Button-->
            <button type="button" class="btn btn-sm btn-primary" onclick="window.open('https://www.youtube.com/watch?v=8KDuTVZgR0Y')">Link</button>

            <!--즐겨찾기 Button-->
            <c:if test="${item.stared eq true}">
              <button type="button" onclick="updateStared(${item.id}, false)" class="btn btn-sm">
                <i class="bi-star-fill"></i>
              </button>
            </c:if>
            <c:if test="${item.stared eq false}">
              <button type="button" onclick="updateStared(${item.id}, true)" class="btn btn-sm">
                <i class="bi-star"></i>
              </button>
            </c:if>

            <!--Edit Button-->
            <button type="button" class="btn btn-sm btn-secondary">
              <i class="bi bi-pencil-square"></i>
              <!--Button 내용-->
            </button>

            <!--Delete Button-->
            <button type="button" onclick="deleteFolder(${item.id})" class="btn btn-sm btn-danger">
              <i class="bi bi-trash"></i>
              <!--공백란-->
            </button>
          </div>
        </div>
      </c:forEach>
      <!--카드-->
      <div class="card p-3" style="width: 17rem; float: left; margin: 20px;">
        <img src="https://cdn.britannica.com/82/152982-050-11159CF4/Daniel-Radcliffe-Rupert-Grint-Emma-Watson-Harry.jpg" class="card-img-top" alt="">
        <div class="card-body">
          <h5 class="card-title">제목</h5>
          <p class="card-text">메모메모</p>
          <p class="card-text">3개의 북마크가 있습니다.</p>
          <div class="text-end">
            <!--즐겨찾기 Button-->
            <button type="button" class="btn btn-sm">
              <i class="bi-star" style="font-size:20px; color: #FDD017; cursor: pointer;"></i>
              <script>
                let i = 0;
                $('i').on('click',function(){
                  if(i === 0){
                    $(this).attr('class','bi-star-fill');
                    i++;
                  } else if(i === 1){
                    $(this).attr('class','bi-star');
                    i--;
                  }
                });
              </script>
            </button>
            <!--자세히 보기 Button-->
            <button type="button" class="btn btn-sm btn-primary">
              <i class="bi bi-search"></i>
            </button>

            <!--Edit Button-->
            <button type="button" class="btn btn-sm btn-secondary">
              <i class="bi bi-pencil-square"></i>
              <!--Button 내용-->
            </button>

            <!--Delete Button-->
            <button type="button" class="btn btn-sm btn-danger">
              <i class="bi bi-trash"></i>
              <!--공백란-->
            </button>
          </div>
        </div>
      </div>
  </div>
</div>

<br>
<%-- paging --%>
<div class="container">
  <nav>
    <ul class="pagination justify-content-center">
      <c:if test="${pagination.previousPageExists}">
        <li class="page-item">
          <c:url value="${pageContext.request.contextPath}/folder" var="url">
            <c:param name="page" value="${pagination.startIndexNum - 10}" />
          </c:url>

          <a class="page-link" href="${url}" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </a>
        </li>
      </c:if>

      <c:forEach begin="${pagination.startIndexNum}" end="${pagination.endIndexNum}" step="1" varStatus="status">
        <c:choose>
          <c:when test="${status.current eq pagination.currentPageNum}">
            <li class="page-item active" aria-current="page">
              <span class="page-link">${status.current}</span>
            </li>
          </c:when>

          <c:otherwise>
            <c:url value="${pageContext.request.contextPath}/folder" var="url">
              <c:param name="not_stared_page" value="${pagination.currentPageNum}" />
              <c:param name="stared_page" value="${status.current}" />
            </c:url>

            <li class="page-item"><a class="page-link" href="${url}">${status.current}</a></li>
          </c:otherwise>
        </c:choose>
      </c:forEach>

      <c:if test="${pagination.nextPageExists}">
        <c:url value="${pageContext.request.contextPath}/folder" var="url">
          <c:param name="page" value="${pagination.startIndexNum + 10}" />
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
</body>
</html>
