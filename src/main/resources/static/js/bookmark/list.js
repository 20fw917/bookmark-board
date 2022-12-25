const deleteBookmark = (id) => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    if (confirm('삭제하시겠습니까? 연관된 게시글까지 모두 삭제됩니다.')) {
        $.ajax({
            url: "/bookmark/delete/" + id,
            type: "DELETE",
            async: false,
            cache: false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(result) {
                if(result) {
                    location.reload();
                } else {
                    alert("삭제에 실패하였습니다.");
                    console.log("DELETE Failed");
                }
            },
            error: function(err) {
                alert("서버 문제로 인해 삭제에 실패하였습니다.");
                console.error(err);
                return false;
            }
        })
    }
}

const updateStared = (id, toModifyStaredStatus) => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: "/bookmark/update/stared/" + id,
        type: "PATCH",
        data: {'to_modify_stared_status': toModifyStaredStatus},
        async: false,
        cache: false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result) {
            if(result) {
                location.reload();
            } else {
                alert("즐겨찾기 작업에 실패하였습니다.");
                console.log("stared FATCH Failed");
            }
        },
        error: function(err) {
            alert("서버 문제로 인해 작업에 실패하였습니다.");
            console.error(err);
        }
    })
}