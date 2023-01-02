const copyFolder = (id) => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    if (confirm('복사하시겠습니까? 폴더 내의 북마크들도 모두 복사됩니다.')) {
        $.ajax({
            url: "/folder/copy/" + id,
            type: "POST",
            async: false,
            cache: false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(result) {
                if(result) {
                    alert("내 북마크, 폴더 함에 복사하였습니다.");
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

// TODO: Like AJAX 요청 만들어야함.
const likeFolder = (id) => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: "/folder/like/" + id,
        type: "POST",
        async: false,
        cache: false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result) {
            if(result) {
                location.reload();
            }
        },
        error: function(err) {
            alert("서버 문제로 인해 좋아요 작업이 실패하였습니다.");
            console.error(err);
        }
    })
}
