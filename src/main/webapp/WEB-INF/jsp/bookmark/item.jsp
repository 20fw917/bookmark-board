<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="padding: 10px;">
  <div class="card">
    <div class="card-body">
      <h5 class="card-title">${param.title}</h5>
      <p class="card-text">${param.memo}</p>
      <c:if test="${param.memo eq null || param.memo eq ''}">
        <div style="padding-bottom: 32px"></div>
      </c:if>
      <div class="row"></div>
      <p class="card-text">${param.url}</p>
      <c:if test="${param.showToolbar eq false}">
        <div class="text-end">
          <a href="${param.url}" class="btn btn-primary">
            <i class="bi bi-globe"></i>
            방문
          </a>
        </div>
      </c:if>

      <c:if test="${param.showToolbar eq true}">
        <a href="${param.url}" class="btn btn-primary">
          <i class="bi bi-globe"></i>
          방문
        </a>

        <span style="float: right;">
      <%-- 즐겨찾기 버튼 --%>
      <button type="button" class="btn btn-sm"
              <c:if test="${param.stared eq true}">
                onclick="updateStared(${param.id}, false)">
                <i class="bi bi-star-fill" style="font-size: 2rem; color: #FDD017"></i>
              </c:if>
        <c:if test="${param.stared eq false}">
          onclick="updateStared(${param.id}, true)">
          <i class="bi bi-star" style="font-size: 2rem; color: #FDD017"></i>
        </c:if>
      </button>

      <%-- 공개 버튼 --%>
      <c:if test="${param.shared eq true}">
        <button type="button" class="btn btn-sm" onclick="updateShared(${param.id}, false)">
          <i class="bi bi-people-fill" style="font-size: 2rem;"></i>
        </button>
      </c:if>
      <c:if test="${param.shared eq false}">
        <button type="button" class="btn btn-sm" onclick="updateShared(${param.id}, true)">
          <i class="bi bi-person" style="font-size: 2rem;"></i>
        </button>
      </c:if>

      <c:url value="${pageContext.request.contextPath}/bookmark/update" var="url">
        <c:param name="id" value="${param.id}" />
      </c:url>
      <a href="${url}" class="btn btn-secondary">
        <i class="bi bi-pencil-square"></i>
        편집
      </a>
      <button onclick="deleteBookmark(${param.id})" class="btn btn-danger">
        <i class="bi bi-trash"></i>
        삭제
      </button>
      <br>
      <div class="text-end">
        <p class="fst-italic">${param.createdAtFormatted}에 추가 됨</p>
      </div>
    </span>
      </c:if>
    </div>
  </div>
</div>
