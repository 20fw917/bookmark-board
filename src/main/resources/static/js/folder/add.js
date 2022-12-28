const setThumbnail = (inputElement) => {
    let preview = document.getElementById('preview');
    let previewArea = document.getElementById('previewArea');
    let deleteRequest = document.getElementById('deleteRequest');

    if (inputElement.files && inputElement.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
            previewArea.style.display = 'block';
            deleteRequest.value = 'false';
        };
        reader.readAsDataURL(inputElement.files[0]);
    } else {
        preview.src = "";
        previewArea.style.display = 'none';
    }
}

const addBookmark = (itemId) => {
    let addedBookmarkArea = document.getElementById('addedBookmarkArea');

    // 중복 체크 로직
    if(document.getElementById('checkedItemCard_' + itemId) !== null) {
        alert("이미 추가된 북마크입니다. 중복 추가는 되지 않습니다.");
        return;
    }

    const title = document.getElementById('itemTitle_' + itemId).innerText;
    const memo = document.getElementById('itemMemo_' + itemId).innerText;
    const url = document.getElementById('itemUrl_' + itemId).innerText;

    let toAddElement = "<div class=\"card w-100\" id=\"checkedItemCard_" + itemId + "\" style=\"margin-bottom: 10px\">\n" +
        "<div class=\"card-body\" style=\"padding: 5px;\"></div>" +
        "<input name=\"checkedItem\" type=\"hidden\" value=\"" + itemId + "\">\n" +
        "<h5 class=\"card-title\" style=\"padding-left: 5px;\">" + title + "</h5>\n" +
        "<p class=\"card-text\" style=\"padding-left: 5px;\">" + memo + "</p>\n" +
        "<p class=\"card-text\" style=\"padding-left: 5px;\">" + url + "</p>\n" +
        "<div class=\"text-end\" style=\"padding: 5px\">\n" +
        "<button type='button' onclick='deleteBookmark(" + itemId + ")' class=\"btn btn-danger\">\n" +
        "<i class=\"bi bi-x\"></i>\n" +
        "삭제\n" +
        "</button>\n" +
        "</div>\n" +
        "</div>"

    addedBookmarkArea.innerHTML += toAddElement;
}

const deleteBookmark = (itemId) => {
    document.getElementById('checkedItemCard_' + itemId).remove();
}

const searchKeyword = (inputElement) => {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: "/bookmark/search",
        type: "POST",
        data: {keyword: inputElement.value},
        async: false,
        cache: false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result) {
            let toAddBookmarkArea = document.getElementById('toAddBookmarkArea');
            while (toAddBookmarkArea.hasChildNodes()) {
                toAddBookmarkArea.removeChild(toAddBookmarkArea.firstChild);
            }

            for(const item of result) {
                addItem(item);
            }
        },
        error: function(err) {
            console.error("에러 발생");
        }
    })
}

const addItem = (item) => {
    let toAddElement = "<div class=\"card w-100\" id=\"itemCard_" + item.id + "\" style=\"margin-bottom: 10px\">\n" +
        "<div class=\"card-body\" style=\"padding: 5px;\"></div>" +
        "<h5 class=\"card-title\" id=\"itemTitle_" + item.id + "\" style=\"padding-left: 5px;\">" + item.title + "</h5>\n" +
        "<p class=\"card-text\" id=\"itemMemo_" + item.id + "\" style=\"padding-left: 5px;\">" + item.memo + "</p>\n" +
        "<p class=\"card-text\" id=\"itemUrl_" + item.id + "\" style=\"padding-left: 5px;\">" + item.url + "</p>\n" +
        "<div class=\"text-end\" style=\"padding: 5px\">\n" +
        "<button type='button' onclick='addBookmark(" + item.id + ")' class=\"btn btn-primary\">\n" +
        "<i class=\"bi bi-plus\"></i>\n" +
        "추가\n" +
        "</button>\n" +
        "</div>\n" +
        "</div>"

    document.getElementById('toAddBookmarkArea').innerHTML += toAddElement;
}

const deleteThumbnail = () => {
    let preview = document.getElementById('preview');
    let inputImage = document.getElementById('inputImage');
    let previewArea = document.getElementById('previewArea');
    let deleteRequest = document.getElementById('deleteRequest');

    inputImage.value= '';
    preview.src = '';
    previewArea.style.display = 'none';
    deleteRequest.value = 'true';
}