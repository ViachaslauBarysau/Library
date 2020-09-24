let bookId = ${bookpagedto.bookDto.id};
let readerCards = new Map();
let mapKey = 0;
let readerCardRecord = {};
const saveButton = document.getElementById('saveButton');
const readerCardId = document.getElementById('readerCardId');
const readerId = document.getElementById('readerId');
const email = document.getElementById('email');
const name = document.getElementById('name');
const borrowDate = document.getElementById('borrowdate');
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

function getDateStringWithoutTime(date) {
    return date.getFullYear() + "-" + (date.getMonth() < 9 ? ('0' + (date.getMonth() + 1)) : (date.getMonth() + 1))
        + "-" + (date.getDate() < 10 ? ('0' + date.getDate()) : (date.getDate()));
}

function getDateStringWithTime(date) {
    return getDateStringWithoutTime(date) + " " + (date.getHours() < 10 ? ('0' + date.getHours()) : (date.getHours())) +
        ":" + (date.getMinutes() < 10 ? ('0' + date.getMinutes()) : (date.getMinutes()))
        + ":" + (date.getSeconds() < 10 ? ('0' + date.getSeconds()) : (date.getSeconds()));
}

function setNewReaderCardProperties() {
    email.readOnly = false;
    name.readOnly = false;
    timePeriodSelect.hidden = false;
    timePeriodSelect.disabled = false
    timePeriodLabel.hidden = false;
    borrowingStatus.hidden = true;
    borrowingStatusLabel.hidden = true;
    borrowingStatus.disabled = false;
    comment.readOnly = false;
}

function setExistingReaderCardProperties() {
    email.readOnly = true;
    name.readOnly = true;
    timePeriodSelect.disabled = true;
    timePeriodSelect.readOnly = true;
    borrowingStatus.hidden = false;
    borrowingStatusLabel.hidden = false;
    borrowingStatus.disabled = false;
    comment.readOnly = false;
}

function setCreatedReaderCardProperties() {
    timePeriodSelect.hidden = false;
    timePeriodLabel.hidden = false;
    timePeriodSelect.disabled = false;
    email.readOnly = true;
    name.readOnly = true;
    borrowingStatusLabel.hidden = true;
    borrowingStatus.hidden = true;
    borrowingStatus.disabled = false;
    comment.readOnly = false;
}

function openModal() {
    let chk_status = totalAmount.checkValidity();
    if (chk_status) {
        let body = document.body;
        body.classList.add('modal-open');
        body.setAttribute('style', "display:block; padding-right: 17px;");
        let myModal = document.getElementById("myModal");
        myModal.classList.add('show');
        myModal.setAttribute('style', "display:block; padding-right: 17px;");
        myModal.removeAttribute('aria-hidden');
    }
}

function closeModal() {
    let body = document.body;
    body.classList.remove('modal-open');
    body.removeAttribute("style");
    let myModal = document.getElementById("myModal");
    myModal.classList.remove('show');
    myModal.setAttribute('style', 'display: none;');
    myModal.setAttribute('aria-hidden', 'true');
}
function createNewReaderCard() {
    if (availableAmount.value == 0) {
        modal.style.display = "block";
    } else {
        saveButton.style.display = 'inline-block';
        saveButton.setAttribute('onclick', "saveNewReaderCard()");
        openModal();
        setNewReaderCardProperties();
        readerCardId.value = 0;
        readerId.value = 0;
        email.value = "";
        name.value = "";
        comment.value = "";
        let dueDate = new Date();
        let fullDueDate = getDateStringWithoutTime(dueDate);
        borrowDate.value = fullDueDate;
        timePeriodSelect.value = "1";
        borrowingStatus.value = "borrowed";
    }
}
async function openExistingReaderCard(id) {
    setExistingReaderCardProperties();
    saveButton.style.display = 'inline-block';
    saveButton.setAttribute('onclick', 'saveExistingReaderCard(' + id + ')');
    openModal();

    let url = 'reader-card?id=' + id;
    let response = await fetch(url);
    readerCardRecord = await response.json();

    readerCardId.value = id;
    readerId.value = readerCardRecord.readerId;
    email.value = readerCardRecord.readerEmail;
    name.value = readerCardRecord.readerName;
    timePeriodSelect.value = readerCardRecord.timePeriod;
    borrowDate.value = readerCardRecord.borrowDate;
    comment.value = readerCardRecord.comment;
    borrowingStatus.value = readerCardRecord.status;

}

function openCreatedReaderCard(index) {
    saveButton.style.display = 'inline-block';
    saveButton.setAttribute('onclick', 'editCreatedReaderCard(' + index + ')');
    openModal();
    let readerCard = readerCards.get(index);
    readerCardId.value = readerCard.readerId;
    email.value = readerCard.readerEmail;
    name.value = readerCard.readerName;
    borrowDate.value = readerCard.borrowDate;
    borrowingStatus.value = readerCard.status;
    timePeriodSelect.value = readerCard.timePeriod;
    comment.value = readerCard.comment;
    setCreatedReaderCardProperties();
}

function editCreatedReaderCard(index) {
    let dueDate = new Date();
    let timePeriod = +timePeriodSelect.options[timePeriodSelect.selectedIndex].value;
    dueDate.setMonth(dueDate.getMonth() + timePeriod);
    let fullDueDate = getDateStringWithoutTime(dueDate);
    let readerCard = readerCards.get(index);
    readerCard.timePeriod = timePeriodSelect.options[timePeriodSelect.selectedIndex].value;
    readerCard.dueDate = fullDueDate;
    readerCard.comment = comment.value;
    document.getElementById("dueDate" + index).innerText = fullDueDate;
    readerCards.set(index, readerCard);
    setBookStatus();
    closeModal();
}

function changeStatusOnExistingReaderCard (id) {
    let returnDate = new Date();
    if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "returned") {
        returnDateValue = getDateStringWithTime(returnDate);
        availableAmount.value++;
        totalAmount.min--;
    } else if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "damaged" ||
        borrowingStatus.options[borrowingStatus.selectedIndex].value == "lost") {
        returnDateValue = getDateStringWithTime(returnDate);
        totalAmount.value--;
        totalAmount.min--;
    } else {
        returnDateValue = "";

    }
    document.getElementById("rd" + id).innerText = returnDateValue;
    setBookStatus();
}

async function setBookStatus() {
    if (availableAmount.value > 0) {
        bookStatus.value = "Available " + availableAmount.value + " out of " + totalAmount.value;
    } else if (availableAmount.value == 0) {
        if (totalAmount.value == 0) {
            bookStatus.value = "Unavailable";
        } else {
            bookStatus.value = "Unavailable (expected to become available on " + await getNearestAvailableDate() + ")";
        }
    }
}

async function getNearestAvailableDate() {
    let url = 'reader-card?bookid=' + bookId;
    let response = await fetch(url, {
        method: 'POST'
    });
    let readerCardsFromDB = await response.json();
    let nearestDate = new Date('3000-12-31');
    readerCardsFromDB.forEach(readerCardFromDB => {
        if (new Date(readerCardFromDB.dueDate) < nearestDate) {
            let readerCardUpdated = false;
            readerCards.forEach(readerCardUI => {
                if (readerCardsFromDB.id == readerCardUI.id) {
                    readerCardUpdated = true;
                }
            })
            if (!readerCardUpdated) {
                nearestDate = new Date(readerCardFromDB.dueDate);
            }
        }
    })
    readerCards.forEach(readerCardUI => {
        if ((readerCardUI.returnDate == null || readerCardUI.returnDate) &&
            new Date(readerCardUI.dueDate) < nearestDate) {
            nearestDate = new Date(readerCardUI.dueDate);
        }
    })
    return getDateStringWithoutTime(nearestDate);
}

function saveExistingReaderCard(id) {
    changeStatusOnExistingReaderCard(id);
    let readerCard = {
        bookId : bookId,
        id : id,
        readerId : readerId.value,
        readerEmail : email.value,
        readerName : name.value,
        borrowDate : readerCardRecord['borrowDate'],
        timePeriod : timePeriodSelect.options[timePeriodSelect.selectedIndex].value,
        dueDate : readerCardRecord['dueDate'],
        status : borrowingStatus.options[borrowingStatus.selectedIndex].value,
        comment : comment.value,
        returnDate : returnDateValue
    }
    readerCards.set(mapKey, readerCard);
    document.getElementById("email" + id)
        .setAttribute('onclick', 'openEditedExistingReaderCard(' + mapKey++ + ')');
    closeModal();
}

function openEditedExistingReaderCard(index) {
    setExistingReaderCardProperties();
    if (readerCards.get(index).status == "returned" && availableAmount.value == 0) {
        borrowingStatus.disabled = true;
    }
    saveButton.style.display = 'inline-block';
    openModal();
    let readerCard = readerCards.get(index);
    readerCardId.value = readerCard.readerId;
    email.value = readerCard.readerEmail;
    name.value = readerCard.readerName;
    timePeriodSelect.value = readerCard.timePeriod;
    borrowDate.value = readerCard.borrowDate;
    borrowingStatus.value = readerCard.status;
    comment.value = readerCard.comment;
    saveButton.setAttribute('onclick', 'saveEditedReaderCard(' + index + ')');
}

function saveAddedReaderCard(index) {
        document.getElementById("email" + index).innerHTML = '<a href="#" id="email' + index +
            '" onclick="openCreatedReaderCard(' + index + ')" data-toggle="modal" data-target="#myModal"' +
            ' class="stretched-link">' + email.value + '</a>';
        document.getElementById('name' + index).innerText = name.value;
        let today = new Date();
        let fullDate = getDateStringWithoutTime(today);
        document.getElementById('borrowDate' + index).innerText = fullDate;
        let dueDate = new Date();
        let timePeriod = +timePeriodSelect.options[timePeriodSelect.selectedIndex].value;
        dueDate.setMonth(dueDate.getMonth() + timePeriod);
        let fullDueDate = getDateStringWithoutTime(dueDate);
        document.getElementById('dueDate' + index).innerText = fullDueDate;
        let readerCard = {
            bookId : bookId,
            id: 0,
            readerEmail : email.value,
            readerName : name.value,
            borrowDate : fullDate,
            timePeriod:  timePeriodSelect.options[timePeriodSelect.selectedIndex].value,
            dueDate : fullDueDate,
            status : "borrowed",
            comment : comment.value,
        }
        readerCards.set(index, readerCard);
        availableAmount.value--;
        totalAmount.min++;
        setBookStatus();
        closeModal();
}

function saveNewReaderCard() {
    document.getElementById("records-message").hidden = true;
    let form = document.getElementById("modal-form");
    let chk_status = form.checkValidity();
    form.reportValidity();
    if (chk_status) {
        const table = document.getElementById("table");
        const tr = document.createElement("tr");
        table.appendChild(tr);
        const td1 = document.createElement("td");
        td1.id = "email" + mapKey;
        const td2 = document.createElement("td");
        td2.id = "name" + mapKey;
        const td3 = document.createElement("td");
        td3.id = "borrowDate" + mapKey;
        const td4 = document.createElement("td");
        td4.id = "dueDate" + mapKey;
        const td5 = document.createElement("td");
        td5.id = "returnDate" + mapKey;
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        saveAddedReaderCard(mapKey++);
        closeModal();
    }
}

function saveEditedReaderCard(index) {
    let returnDate = new Date();
    let readerCard = readerCards.get(index);
    if (readerCard.status == "damaged" || readerCard.status == "lost") {
        if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "borrowed") {
            totalAmount.value++;
            totalAmount.min++;
            returnDateValue = "";
        } else if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "returned") {
            totalAmount.value++;
            availableAmount.value++;
            returnDateValue = getDateStringWithTime(returnDate);
        }
        document.getElementById("rd" + readerCard.id).innerText = returnDateValue;
    } else if (readerCard.status == "returned") {
        if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "damaged" ||
            borrowingStatus.options[borrowingStatus.selectedIndex].value == "lost") {
            totalAmount.value--;
            availableAmount.value--;
            returnDateValue = getDateStringWithTime(returnDate);
        } else if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "borrowed") {
            availableAmount.value--;
            totalAmount.min++;
            returnDateValue = "";
        }
        document.getElementById("rd" + readerCard.id).innerText = returnDateValue;
    } else {
        changeStatusOnExistingReaderCard(readerCard.id);
    }
    setBookStatus();
    readerCard.status = borrowingStatus.value;
    readerCard.comment = comment.value;
    readerCard.returnDate = returnDateValue;
    closeModal();
}

async function openClosedReaderCard(id) {
    openModal();
    let url = 'reader-card?id=' + id;
    let response = await fetch(url);
    let readerCard = await response.json();

    borrowingStatus.value = readerCard.status;
    borrowingStatus.disabled = true;
    borrowingStatus.hidden = false;
    borrowingStatusLabel.hidden = false;
    email.value = readerCard.readerEmail;
    email.readOnly = true;
    name.value = readerCard.readerName;
    name.readOnly = true;
    borrowDate.value = readerCard.borrowDate;
    borrowDate.readOnly = true;
    comment.value = readerCard.comment;
    comment.readOnly = true;
    timePeriodSelect.hidden = false;
    timePeriodLabel.hidden = false;
    timePeriodSelect.value = readerCard.timePeriod;
    timePeriodSelect.disabled = true;
    saveButton.style.display = 'none';
}

async function sendForm() {
    let form = document.getElementById("bookform");
    let chk_status = form.checkValidity();
    form.reportValidity();
    if (chk_status) {
        let obj = [];
        readerCards.forEach((value, key)=>{
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
    setTimeout(function(){
        snackbar.className = snackbar.className.replace("show", "");
        }, 3000);
}

async function getEmailsByPattern(pattern) {
    let autocomplete = document.getElementById('autocomplete');
    if (pattern.length >=3) {
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

async  function autocompleteEmail(email) {
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

function onAmountChange(count) {
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
}

function deleteGenre(element) {
    if (document.getElementsByName("span-genres").length > 1) {
        element.parentNode.remove();
    }
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

document.addEventListener("DOMContentLoaded", ready);
function ready() {
    totalAmount.min = totalAmount.value - availableAmount.value;
}
