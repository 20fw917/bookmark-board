<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav>
  <ul class="pagination justify-content-center">
    <c:if test="${param.previousPageExists}">
      <c:url value="${pageContext.request.contextPath}/bookmark" var="minusTenUrl">
        <c:param name="${param.anotherPageName}" value="${param.anotherPageCurrentPage}" />
        <c:param name="${param.currentPageParam}" value="${param.startIndexNum - 10}" />
      </c:url>
      <li class="page-item">
        <a class="page-link" href="${minusTenUrl}" aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
    </c:if>

    <c:forEach begin="${param.startIndexNum}" end="${param.endIndexNum}" step="1" varStatus="status">
      <c:choose>
        <c:when test="${status.current eq param.currentPageNum}">
          <li class="page-item active" aria-current="page">
            <span class="page-link">${status.current}</span>
          </li>
        </c:when>

        <c:otherwise>
          <c:url value="${pageContext.request.contextPath}/bookmark" var="url">
            <c:param name="${param.anotherPageName}" value="${param.anotherPageCurrentPage}" />
            <c:param name="${param.currentPageParam}" value="${status.current}" />
          </c:url>

          <li class="page-item"><a class="page-link" href="${url}">${status.current}</a></li>
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <c:if test="${param.nextPageExists}">
      <c:url value="${pageContext.request.contextPath}/bookmark" var="plusTenUrl">
        <c:param name="${param.anotherPageName}" value="${param.anotherPageCurrentPage}" />
        <c:param name="${param.currentPageParam}" value="${param.startIndexNum + 10}" />
      </c:url>
      <li class="page-item">
        <a class="page-link" href="${plusTenUrl}" aria-label="Next">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
    </c:if>
  </ul>
</nav>