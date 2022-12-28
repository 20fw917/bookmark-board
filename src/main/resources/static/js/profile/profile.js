const updateProfileImage = () => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    const imageInput = $("#inputProfileImage")[0];
    // 파일을 여러개 선택할 수 있으므로 files 라는 객체에 담긴다.
    console.log("inputProfileImage: ", imageInput.files)

    if(imageInput.files.length === 0){
        alert("파일을 선택해주세요");
        return;
    }

    const formData = new FormData();
    formData.append("image", imageInput.files[0]);

    $.ajax({
        type: "POST",
        url: "/profile/update/profile_image",
        processData: false,
        contentType: false,
        data: formData,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result) {
            location.reload();
        },
        err: function(err){
            console.log("err:", err)
        }
    })
}

const deleteProfileImage = () => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type: "DELETE",
        url: "/profile/delete/profile_image",
        processData: false,
        contentType: false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result) {
            location.reload();
        },
        err: function(err){
            console.log("err:", err)
        }
    })
}