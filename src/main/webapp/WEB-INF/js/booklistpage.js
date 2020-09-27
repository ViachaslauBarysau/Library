function hideUnavailable (checkbox) {
    if (checkbox.checked) {
        let url = "http://localhost:8080/lib-app?command=GET_BOOK_LIST&hideunavailable=on&page=1";
        document.location.href = url;
    } else {
        let url = "http://localhost:8080/lib-app?command=GET_BOOK_LIST&page=1";
        document.location.href = url;
    }
}