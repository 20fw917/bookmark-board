<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <title>폴더 추가</title>
  <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
  <sec:csrfMetaTags/>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/folder/add.js"></script>
  <link href="${pageContext.request.contextPath}/static/css/common/floating_labels.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>
<div class="py-5 text-center">
  <c:if test="${isModify eq true}">
    <h2>폴더 수정</h2>
  </c:if>
  <c:if test="${isModify eq false}">
    <h2>폴더 추가</h2>
  </c:if>
</div>

<div class="container">
  <form:form enctype="multipart/form-data">
    <div class="row">
      <c:if test="${isModify eq true}">
        <input type="hidden" name="id" value="${toModifyItem.id}">
      </c:if>

      <div>
        <label for="inputImage">폴더 섬네일</label>
        <div class="form-label-group position-relative">
          <input class="form-control" name="folderThumbnail" type="file" id="inputImage" accept="image/*" onchange="setThumbnail(this);"/>
          <br>
        </div>

        <img id="preview" style="display: none; width: 300px; height: 300px"/>
        <br>
      </div>

      <br>
      <div class="col-md-7 mb-3">
        <div class="form-label-group position-relative">
          <input type="text" class="form-control" name="title" id="inputTitle" placeholder="제목" <c:if test="${isModify eq true}">value="${toModifyItem.title}"</c:if> maxlength="100" required>
          <label for="inputTitle">제목</label>
          <small class="ml-1 form-text text-muted">제목은 100자까지 지정할 수 있습니다.</small>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-7 mb-3">
        <div class="form-floating">
          <textarea class="form-control" name="memo" id="inputMemo" rows="3" maxlength="1000" ><c:if test="${isModify eq true}">${toModifyItem.memo}</c:if></textarea>
          <label for="inputMemo">메모</label>
          <small class="ml-1 form-text text-muted">메모는 1,000자까지 입력하실 수 있습니다.</small>
        </div>
      </div>
    </div>

    <br>
    <div class="row">
      <div class="col-md-5 mb-3">
        <h4 class="h4">추가할 북마크</h4>
        <div class="form-label">
          <div class="border form-outline overflow-auto" style="height: 300px; padding: 10px">
            <input onkeyup="searchKeyword(this)" type="search" id="itemSearch" class="form-control" placeholder="북마크 검색"/>
            <p class="text-justify">추가할 북마크를 고르세요.</p>
            <div id="toAddBookmarkArea">
              <c:forEach items="${bookmarkList}" var="item">
                <div class="card w-100" id="itemCard_${item.id}" style="margin-bottom: 10px">
                  <div class="card-body" style="padding: 5px;"></div>
                    <%--                  <input class="form-check-input" name="check_${item.id}" type="checkbox" value="${item.id}" id="check_${item.id}">--%>
                  <h5 class="card-title" id="itemTitle_${item.id}" style="padding-left: 5px;">${item.title}</h5>
                  <p class="card-text" id="itemMemo_${item.id}" style="padding-left: 5px;">${item.memo}</p>
                  <p class="card-text" id="itemUrl_${item.id}" style="padding-left: 5px;">${item.url}</p>
                  <div class="text-end" style="padding: 5px">
                    <button type="button" onclick="addBookmark('${item.id}')" class="btn btn-primary">
                      <i class="bi bi-plus"></i>
                      추가
                    </button>
                  </div>
                </div>
              </c:forEach>
            </div>
          </div>
        </div>
      </div>

      <div class="col-md-5 mb-3">
        <h4 class="h4">추가된 북마크</h4>
        <div class="form-label">
          <div class="border form-outline overflow-auto" id="addedBookmarkArea" style="height: 300px; padding: 10px">
              <%-- 추가 버튼 누르면 여기로 들어감 --%>
          </div>
        </div>
      </div>
    </div>

    <div class="form-check">
      <input class="form-check-input" name="isShared" type="checkbox" value="true" id="shareCheckBox" <c:if test="${isModify eq true}"><c:if test="${toModifyItem.shared}">checked</c:if></c:if>>
      <label class="form-check-label" for="shareCheckBox">
        이 북마크를 프로필에 공유합니다.
      </label>
    </div>

    <div class="form-check">
      <input class="form-check-input" name="isStared" type="checkbox" value="true" id="starCheckbox" <c:if test="${isModify eq true}"><c:if test="${toModifyItem.stared}">checked</c:if></c:if>>
      <label class="form-check-label" for="starCheckbox">
        이 북마크를 즐겨찾기에 등록합니다.
      </label>
    </div>

    <hr class="mb-4">
    <div>
      <button class="btn btn-primary btn-lg" type="submit">추가</button>
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
          <a href="${pageContext.request.contextPath}/folder"><button type="button" class="btn btn-danger">취소하고 이동</button></a>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
