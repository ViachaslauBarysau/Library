let bookId = document.getElementById('bookId').value;
let readerCards = new Map();
let mapKey = 0;
let readerCardRecord = {};
const saveButton = document.getElementById('saveButton');
const readerCardId = document.getElementById('readerCardId');
const readerId = document.getElementById('readerId');
const email = document.getElementById('email');
const name = document.getElementById('name');
const borrowDate = document.getElementById('borrowdate');
const borrowDateLabel = document.getElementById("borrowDateLabel");
const timePeriodLabel = document.getElementById('timeperiodlabel');
const timePeriodSelect = document.getElementById('timeperiod');
const borrowingStatus = document.getElementById('borrowingStatus');
const borrowingStatusLabel = document.getElementById('statuslabel');
const availableAmount = document.getElementById('availableAmount');
const totalAmount = document.getElementById('totalAmount');
const bookStatus = document.getElementById('bookstatus');
const comment = document.getElementById('comment');
let modal = document.getElementById('myErrorModal');
let returnDateValue;

async function sendForm() {
    let form = document.getElementById("bookform");
    let chk_status = form.checkValidity();
    form.reportValidity();
    if (chk_status) {
        let obj = [];
        readerCards.forEach((value, key) => {
            obj.push(value);
        })
        let formData = new FormData(form);
        formData.append("readerCards", JSON.stringify(obj));
        let url = 'lib-app?command=ADD_BOOK';
        if (bookId > 0) {
            url = 'lib-app?command=EDIT_BOOK';
        }
        let response = await fetch(url, {
            method: 'POST',
            body: formData
        })
        if (response.redirected) {
            window.location.href = response.url;
        } else {
            let errors = await response.json();
            showErrors(errors);
        }
    }
}

function showErrors(errors) {
    let snackbar = document.getElementById("snackbar");
    snackbar.innerHTML = "";
    errors.forEach((element) => {
        snackbar.insertAdjacentHTML('beforeend', '<p>' + element + '</p>')
    })
    snackbar.className = "show";
    setTimeout(function () {
        snackbar.className = snackbar.className.replace("show", "");
    }, 3000);
}

async function getEmailsByPattern(pattern) {
    let autocomplete = document.getElementById('autocomplete');
    if (pattern.length >= 3) {
        let url = "reader?pattern=" + pattern.toLowerCase();
        let response = await fetch(url);
        let emails = await response.json();
        let vars = '';
        for (let i in emails) {
            vars += '<li onclick="autocompleteEmail(\'' + emails[i] + '\')">' + emails[i] + '</li>';
        }
        while (autocomplete.firstChild) {
            autocomplete.removeChild(autocomplete.firstChild);
        }
        autocomplete.classList.remove("hidden");
        autocomplete.insertAdjacentHTML('beforeend', '<ul>' + vars + '</ul>');
    } else {
        autocomplete.classList.add("hidden");
    }
}

async function autocompleteEmail(email) {
    document.getElementById('email').value = email;
    let url = "reader?email=" + email.toLowerCase();
    let response = await fetch(url, {method: 'POST'});
    let readerName = await response.json();
    name.value = readerName;
    closeAutocomplete();
}

function closeAutocomplete() {
    let autocomplete = document.getElementById('autocomplete');
    autocomplete.classList.add("hidden");
}

function onAmountChange() {
    let chk_status = totalAmount.checkValidity();
    if (chk_status) {
        availableAmount.value = totalAmount.value - totalAmount.min
        setBookStatus();
    }
}

window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function deleteAuthor(element) {
    if (document.getElementsByName("span-authors").length > 1) {
        element.parentNode.remove();
    }
    validateAuthorsGenres(document.getElementsByName("authors")[0]);
}

function deleteGenre(element) {
    if (document.getElementsByName("span-genres").length > 1) {
        element.parentNode.remove();
    }
    validateAuthorsGenres(document.getElementsByName("genres")[0]);
}

function addGenreField() {
    if (document.getElementsByName("span-genres").length < 5) {
        let newElement = document.getElementsByName("span-genres")[0].cloneNode(true);
        newElement.firstChild.value = "";
        document.getElementsByName("span-genres")[0].parentNode.appendChild(newElement);
    }
}

function addAuthorField() {
    if (document.getElementsByName("span-authors").length < 5) {
        let newElement = document.getElementsByName("span-authors")[0].cloneNode(true);
        newElement.firstChild.value = "";
        document.getElementsByName("span-authors")[0].parentNode.appendChild(newElement);
    }
}



