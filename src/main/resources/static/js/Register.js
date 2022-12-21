function checkUsername(inputElement)  {
    inputElement.classList.remove('is-invalid');
    inputElement.classList.remove('is-valid');
    inputElement.value = inputElement.value.replace(/\s/gi, "");

    const regex = /^[a-z0-9]{4,30}$/;
    if(regex.test(inputElement.value)) {
        inputElement.className += ' is-valid';
        return true;
    } else {
        inputElement.className += ' is-invalid';
        return false;
    }
}

function checkPassword(inputElement)  {
    inputElement.classList.remove('is-invalid');
    inputElement.classList.remove('is-valid');
    inputElement.value = inputElement.value.replace(/\s/gi, "");

    if(inputElement.value.length < 8) {
        return;
    }

    const regex = /^[a-zA-Z\\d`~!@#$%^&*()-_=+]{8,30}$/;
    if(regex.test(inputElement.value)) {
        inputElement.className += ' is-valid';
        return true;
    } else {
        inputElement.className += ' is-invalid';
        return false;
    }
}

const checkUsernameAtRegister = (inputElement) => {
    inputElement.classList.remove('is-invalid');
    inputElement.classList.remove('is-valid');

    if(inputElement.value.length < 4) {
        return;
    }

    if(checkUsername(inputElement)) {
        // 규칙에 맞는 경우
        inputElement.className += ' is-valid';

        document.getElementById('usernameValidTooltip').innerText = "아이디가 유효합니다. ";
        checkUsernameExists(inputElement);
    } else {
        // 규칙을 어겨 사용 불가능한 경우
        inputElement.className += ' is-invalid';

        document.getElementById('usernameInvalidTooltip').innerText = "아이디 규칙을 지켜주세요.";
    }
}

const checkUsernameExists = (inputElement) => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    inputElement.classList.remove('is-invalid');
    inputElement.classList.remove('is-valid');

    $.ajax({
        url:"/user/username_check",
        type:"POST",
        data:{username: inputElement.value},
        async: false,
        cache: false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result) {
            if(result) {
                // 사용 가능한 경우
                inputElement.className += ' is-valid';

                document.getElementById('usernameValidTooltip').innerText = "중복되지 않아 사용할 수 있는 아이디입니다.";
            } else {
                // 중복이라 사용 불가능한 경우
                inputElement.className += ' is-invalid';

                document.getElementById('usernameInvalidTooltip').innerText = "중복된 아이디입니다.";
            }
            return result;
        },
        error: function(err) {
            console.error("에러 발생");
            inputElement.className += ' is-invalid';

            document.getElementById('usernameInvalidTooltip').innerText = "중복 체크 중 오류가 발생하였습니다.";

            return false;
        }
    })
}

const checkEmail = (inputElement) => {
    const regex = /^([\w._\-])*[a-zA-Z0-9]+([\w._\-])*([a-zA-Z0-9])+([\w._\-])+@([a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,8}$/

    inputElement.classList.remove('is-invalid');
    inputElement.classList.remove('is-valid');

    if(regex.test(inputElement.value)) {
        // 규칙에 맞는 경우
        inputElement.className += ' is-valid';

        document.getElementById('emailValidTooltip').innerText = "이메일이 유효합니다. ";
        checkEmailExists(inputElement);
        return true;
    } else {
        // 규칙을 어겨 사용 불가능한 경우
        inputElement.className += ' is-invalid';

        document.getElementById('emailInvalidTooltip').innerText = "이메일 규칙을 지켜주세요.";
        return false;
    }
}


const checkEmailExists = (inputElement) => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    inputElement.classList.remove('is-invalid');
    inputElement.classList.remove('is-valid');

    $.ajax({
        url:"/user/email_check",
        type:"POST",
        data:{email: inputElement.value},
        async: false,
        cache: false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result) {
            if(result) {
                // 사용 가능한 경우
                inputElement.className += ' is-valid';

                document.getElementById('emailValidTooltip').innerText = "중복되지 않아 사용할 수 있는 이메일입니다.";
            } else {
                // 중복이라 사용 불가능한 경우
                inputElement.className += ' is-invalid';

                document.getElementById('emailInvalidTooltip').innerText = "중복된 이매일입니다.";
            }
            return result;
        },
        error: function(err) {
            console.error("에러 발생: " + err);
            inputElement.className += ' is-invalid';

            document.getElementById('usernameInvalidTooltip').innerText = "중복 체크 중 오류가 발생하였습니다.";

            return false;
        }
    })
}

const checkNickname = (inputElement) => {
    inputElement.classList.remove('is-invalid');
    inputElement.classList.remove('is-valid');
    inputElement.value = inputElement.value.replace(/\s/gi, "");

    if(inputElement.value.length < 2) {
        return;
    }

    const regex = /[`~!@#$%^&*()\-_=+\\|[\]{};:'",.<>\/?]$/g

    if(!regex.test(inputElement.value)) {
        // 규칙에 맞는 경우
        inputElement.className += ' is-valid';

        document.getElementById('nicknameValidTooltip').innerText = "닉네임이 유효합니다. ";
        checkNicknameExists(inputElement);
        return true;
    } else {
        // 규칙을 어겨 사용 불가능한 경우
        inputElement.className += ' is-invalid';

        document.getElementById('usernameInvalidTooltip').innerText = "닉네임 규칙을 지켜주세요.";
        return false;
    }
}

const checkNicknameExists = (inputElement) => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    inputElement.classList.remove('is-invalid');
    inputElement.classList.remove('is-valid');

    $.ajax({
        url:"/user/nickname_check",
        type:"POST",
        data:{nickname: inputElement.value},
        async: false,
        cache: false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result) {
            if(result) {
                // 사용 가능한 경우
                inputElement.className += ' is-valid';

                document.getElementById('emailValidTooltip').innerText = "중복되지 않아 사용할 수 있는 닉네임입니다.";
            } else {
                // 중복이라 사용 불가능한 경우
                inputElement.className += ' is-invalid';

                document.getElementById('emailInvalidTooltip').innerText = "중복된 이매일입니다.";
            }
            return result;
        },
        error: function(err) {
            console.error("에러 발생: " + err);
            inputElement.className += ' is-invalid';

            document.getElementById('usernameInvalidTooltip').innerText = "중복 체크 중 오류가 발생하였습니다.";

            return false;
        }
    })
}


const checkName = (inputElement) => {
    inputElement.classList.remove('is-invalid');
    inputElement.classList.remove('is-valid');
    inputElement.value = inputElement.value.replace(/\s/gi, "");

    if(inputElement.value.length < 2) {
        return;
    }

    const regex = /^[ㄱ-ㅎ|가-힣]{2,10}$/;

    if(regex.test(inputElement.value)) {
        inputElement.className += ' is-valid';
        return true;
    } else {
        inputElement.className += ' is-invalid';
        return false;
    }
}

const checkPasswordRe = (inputElement) => {
    inputElement.classList.remove('is-invalid');
    inputElement.classList.remove('is-valid');
    inputElement.value = inputElement.value.replace(/\s/gi, "");

    const originalPassword = document.getElementById('inputPassword').value;

    if(inputElement.value !== originalPassword) {
        inputElement.className += ' is-invalid';
        return false;
    } else {
        inputElement.className += ' is-valid';
        return true;
    }
}

// 마지막으로 FE에서 체크
const registerSubmitCheck = (formElement) => {
    if(!checkName(formElement['name'])) {
        return false;
    }

    if(!checkUsername(formElement['username'])) {
        return false;
    }

    if(!checkNickname(formElement['nickname'])) {
        return false;
    }

    if(!checkEmail(formElement['email'])) {
        return false;
    }

    if(!checkPassword(formElement['password'])) {
        return false;
    }

    return checkPasswordRe(formElement['passwordRe']);
}

