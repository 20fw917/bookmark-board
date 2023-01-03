const copyBookmark = (id) => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    if (confirm('복사하시겠습니까?')) {
        $.ajax({
            url: "/bookmark/copy/" + id,
            type: "POST",
            async: false,
            cache: false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(result) {
                if(result) {
                    alert("내 북마크 함에 복사하였습니다.");
                    location.reload();
                }
            },
            error: function(err) {
                alert("서버 문제로 인해 복사가 실패하였습니다.");
                console.error(err);
            }
        })
    }
}

const likeBookmark = (id, toModifyLikeStatus) => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: "/bookmark/like/" + id,
        type: "PATCH",
        data: {'to_modify_liked_status': toModifyLikeStatus},
        async: false,
        cache: false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result) {
            location.reload();
        },
        error: function(err) {
            alert("서버 문제로 인해 좋아요 작업이 실패하였습니다.");
            console.error(err);
        }
    })
}
