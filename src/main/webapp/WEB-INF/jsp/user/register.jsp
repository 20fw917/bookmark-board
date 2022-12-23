<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>회원 가입</title>
    <jsp:include page="/WEB-INF/jsp/include/bootstrap.jsp"/>
    <link href="${pageContext.request.contextPath}/static/css/common/floating_labels.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/Register.js"></script>
    <sec:csrfMetaTags/>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/include/header.jsp"/>

<div class="container">
    <div class="py-5 text-center">
        <img class="d-block mx-auto mb-4" src="" alt="" width="72" height="72">
        <h2>회원 가입</h2>
    </div>

    <div>
        <form:form class="needs-validation">
            <div class="row">
                <div class="col-md-7 mb-3">
                    <div class="form-label-group position-relative">
                        <input type="text" class="form-control" name="username" id="inputUsername"
                               placeholder="아이디" onkeyup="checkUsernameAtRegister(this)" maxlength="30" required>
                        <label for="inputUsername">아이디</label>
                        <small class="ml-1 form-text text-muted">아이디는 공백 없이 영문 소문자, 숫자 조합 4~30자로 지정할 수 있습니다.</small>
                        <div id="usernameInvalidTooltip" class="invalid-tooltip" style="width: 100%;">
                            아이디 규칙을 지켜주세요.
                        </div>
                        <div id="usernameValidTooltip" class="valid-tooltip" style="width: 100%;">
                            아이디가 유효합니다.
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-7 mb-3">
                    <div class="form-label-group position-relative">
                        <input type="text" class="form-control" name="nickname" id="inputNickname"
                               placeholder="닉네임" onkeyup="checkNickname(this)" minlength="2" maxlength="15" required>
                        <label for="inputNickname">닉네임</label>
                        <small class="ml-1 form-text text-muted">닉네임은 공백, 특수문자 없이 2~15자로 지정할 수 있습니다.</small>
                        <div id="nicknameInvalidTooltip" class="invalid-tooltip" style="width: 100%;">
                            닉네임 규칙을 지켜주세요.
                        </div>
                        <div id="nicknameValidTooltip" class="valid-tooltip" style="width: 100%;">
                            닉네임이 유효합니다.
                        </div>
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <div class="form-label-group position-relative">
                    <input type="email" class="form-control" name="email" id="inputEmail" maxlength="320" placeholder="이메일"
                    onkeyup="checkEmail(this)" required>
                    <label for="inputEmail">이메일</label>
                    <small class="ml-1 form-text text-muted">이메일은 아이디/비밀번호 찾기에 사용됩니다. 사용하는 이메일로 적어주세요.</small>
                    <div id="emailInvalidTooltip" class="invalid-tooltip" style="width: 100%;">
                        RFC 2822 국제 표준에 맞는 이메일을 입력해주세요.
                    </div>
                    <div id="emailValidTooltip" class="valid-tooltip" style="width: 100%;">
                        닉네임이 유효합니다.
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <div class="form-label-group position-relative">
                    <input type="password" class="form-control" name="password" id="inputPassword" placeholder="비밀번호"
                           onkeyup="checkPassword(this)" minlength="8" maxlength="30" required>
                    <label for="inputPassword">비밀번호</label>
                    <small class="ml-1 form-text text-muted">비밀번호는 공백 제외 영문, 숫자, 특수문자 조합으로 8~30자로 지정할 수 있습니다.</small>
                    <div class="invalid-tooltip">
                        비밀번호 규칙을 지켜주세요.
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <div class="form-label-group position-relative">
                    <input type="password" class="form-control" name="passwordRe"
                           id="inputPasswordRe" placeholder="비밀번호 확인" minlength="8" maxlength="30" onkeyup="checkPasswordRe(this)" required>
                    <label for="inputPasswordRe">비밀번호 확인</label>
                    <small class="ml-1 form-text text-muted">위에 기재한 비밀번호와 동일하게 입력하세요.</small>
                    <div class="invalid-tooltip">
                        입력한 비밀번호가 같지 않습니다.
                    </div>
                </div>
            </div>

            <hr class="mb-4">
            <div>
                <button class="btn btn-primary btn-lg" onsubmit="return registerSubmitCheck(this)" type="submit">회원 가입</button>
                <button type="button" class="btn btn-secondary btn-lg" data-bs-toggle="modal" data-bs-target="#cancelModal">
                    취소
                </button>
            </div>
        </form:form>
    </div>
</div>

<div class="container">
    <!-- Modal -->
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
                    <a href="/main"><button type="button" class="btn btn-danger">취소하고 이동</button></a>
                </div>
            </div>
        </div>
</div>
</div>

</body>
</html>