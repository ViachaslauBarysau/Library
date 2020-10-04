function validateAuthorsGenres(element) {
    let siblingsList = document.getElementsByName(element.getAttribute("name"));
    for (let i = 0; i < siblingsList.length; i++) {
        let marker = 0;
        for (let j = 0; j < siblingsList.length; j++) {
            if ((i != j) && (siblingsList[i].value.toLowerCase() == siblingsList[j].value.toLowerCase())) {
                marker++;
            }
        }
        if (marker > 0) {
            siblingsList[i].setCustomValidity("Fields must be distinct.");
        } else {
            siblingsList[i].setCustomValidity("");
        }
    }
}

function validateIsbn() {
    let isbn = document.getElementById("isbn");
    if (checkIsbn(isbn.value)) {
        isbn.setCustomValidity("")
    } else {
        isbn.setCustomValidity("Wrong ISBN.")
    }
}

function checkIsbn(isbn) {
    let sum,
        weight,
        digit,
        check,
        i;
    let isbnDigits = isbn.toString().replace(/[^0-9X]/gi, '');
    if (isbnDigits.length != 10 && isbnDigits.length != 13) {
        return false;
    }
    if (isbnDigits.length == 13) {
        sum = 0;
        for (i = 0; i < 12; i++) {
            digit = parseInt(isbnDigits[i]);
            if (i % 2 == 1) {
                sum += 3 * digit;
            } else {
                sum += digit;
            }
        }
        check = (10 - (sum % 10)) % 10;
        return (check == isbnDigits[isbnDigits.length - 1]);
    }
    if (isbnDigits.length == 10) {
        weight = 10;
        sum = 0;
        for (i = 0; i < 9; i++) {
            digit = parseInt(isbnDigits[i]);
            sum += weight * digit;
            weight--;
        }
        check = 11 - (sum % 11);
        if (check == 10) {
            check = 'X';
        }
        return (check == isbnDigits[isbnDigits.length - 1].toUpperCase());
    }
}