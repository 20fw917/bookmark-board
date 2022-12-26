const setThumbnail = (inputElement) => {
    let preview = document.getElementById('preview');

    if (inputElement.files && inputElement.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
        };
        reader.readAsDataURL(inputElement.files[0]);
    } else {
        preview.src = "";
        preview.style.display = 'none';
    }
}