<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <title>북마크 등록</title>
  <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
  <link href="${pageContext.request.contextPath}/static/css/common/floating_labels.css" rel="stylesheet">
  <sec:csrfMetaTags/>
</head>

<body>
  <jsp:include page="/WEB-INF/jsp/include/header.jsp"/>

  <div class="py-5 text-center">
    <c:if test="${isModify eq true}">
      <h2>북마크 수정</h2>
    </c:if>
    <c:if test="${isModify eq false}">
      <h2>북마크 등록</h2>
    </c:if>
  </div>

  <div class="container">
    <form:form class="needs-validation">
      <div class="row">
        <div class="col-md-7 mb-3">
          <div class="form-label-group position-relative">
            <input type="text" class="form-control" name="title" id="inputTitle" placeholder="제목" maxlength="100" required>
            <label for="inputTitle">제목</label>
            <small class="ml-1 form-text text-muted">제목은 100자까지 지정할 수 있습니다.</small>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-7 mb-3">
          <div class="form-floating">
            <textarea class="form-control" name="memo" id="inputMemo" rows="3"></textarea>
            <label for="inputMemo">메모</label>
          </div>
        </div>
      </div>

      <br>
      <div class="row">
        <div class="col-md-7 mb-3">
          <div class="form-label-group position-relative">
            <input type="url" class="form-control" name="url" id="inputURL" placeholder="URL" required>
            <label for="inputURL">URL</label>
          </div>
        </div>
      </div>

      <div class="form-check">
        <input class="form-check-input" name="isShared" type="checkbox" value="true" id="shareCheckBox">
        <label class="form-check-label" for="shareCheckBox">
          이 북마크를 프로필에 공유합니다.
        </label>
      </div>

      <div class="form-check">
        <input class="form-check-input" name="isStared" type="checkbox" value="true" id="starCheckbox">
        <label class="form-check-label" for="starCheckbox">
          이 북마크를 즐겨찾기에 등록합니다.
        </label>
      </div>

      <hr class="mb-4">
      <div>
        <button class="btn btn-primary btn-lg" onsubmit="return registerSubmitCheck(this)" type="submit">저장</button>
        <button type="button" class="btn btn-secondary btn-lg" data-bs-toggle="modal" data-bs-target="#cancelModal">
          취소
        </button>
      </div>
    </form:form>
  </div>

  <div class="container">
    <!-- 취소 Modal -->
    <div style="margin-top: 20%" id="cancelModal" class="modal fade"  tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">경고</h4>
          </div>
          <div class="modal-body">
            취소하면 입력한 데이터를 모두 잃습니다. 계속 하시겠습니까?
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">계속 진행</button>
            <a href="/"><button type="button" class="btn btn-danger">취소하고 이동</button></a>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
