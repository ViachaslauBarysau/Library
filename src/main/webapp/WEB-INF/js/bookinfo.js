let readerCards = new Map();
let mapKey = 0;
let readerCardRecord = {};
const availableBooks = ${bookpagedto.bookDto.availableAmount};
const totalBooks = ${bookpagedto.bookDto.totalAmount};
const nearestAvailableDate = '${bookpagedto.nearestAvailableDate}';
const nextNearestAvailableDate = '${bookpagedto.nextNearestAvailableDate}';
const nearestAvailableDateID = '${bookpagedto.nearestAvailableDateID}';
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
    let body = document.body;
    body.classList.add('modal-open');
    body.setAttribute('style', "display:block; padding-right: 17px;");
    let myModal = document.getElementById("myModal");
    myModal.classList.add('show');
    myModal.setAttribute('style', "display:block; padding-right: 17px;");
    myModal.removeAttribute('aria-hidden');
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
function openNewReaderCard() {

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
async function openExistingReaderCard(id) {
    setExistingReaderCardProperties();
    saveButton.style.display = 'inline-block';
    saveButton.setAttribute('onclick', 'saveExistingReaderCard(' + id + ')');
    openModal();

    let url = 'rdr-crd?id=' + id;
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
    closeModal();
}

// function saveReaderCard() {
//     e.preventDefault();
//     let ele = document.getElementById("modal-form");
//     let chk_status = ele.checkValidity();
//     ele.reportValidity();
//     if (chk_status) {
//         console.log('valid')
//         document.getElementById("totalAmount").readOnly = true;
//         document.getElementById("availableAmount").readOnly = true;
//             saveNewReaderCard();
//         closeModal();
//     }
// }

function changeStatusOnExistingReaderCard (id) {
    let returnDate = new Date();
    if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "returned") {
        document.getElementById("rd" + id).innerText = getDateStringWithTime(returnDate);
        returnDateValue = getDateStringWithTime(returnDate);
        totalAmount.value = totalBooks;
        availableAmount.value = availableBooks - 0 + 1;
        bookStatus.value = "Available " + availableAmount.value + " out of " + totalAmount.value;
    } else if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "damaged" ||
        borrowingStatus.options[borrowingStatus.selectedIndex].value == "lost") {
        document.getElementById("rd" + id).innerText = getDateStringWithTime(returnDate);
        returnDateValue = getDateStringWithTime(returnDate);
        totalAmount.value = totalBooks - 1;
        availableAmount.value = availableBooks;
        setBookStatus();
    } else {
        totalAmount.value = totalBooks;
        availableAmount.value = availableBooks;
        returnDateValue = "";
        document.getElementById("rd" + id).innerText = "";
        setBookStatus();
    }
}

function setBookStatus() {

    if (availableAmount.value > 0) {
        bookStatus.value = "Available " + availableAmount.value + " out of " + totalAmount.value;
    } else if (availableAmount.value == 0) {
        if (totalAmount.value == 0) {
            bookStatus.value = "Unavailable";
        } else {
            if (readerCardId.value == nearestAvailableDateID) {
                if (!Boolean(returnDateValue)) {
                    bookStatus.value = "Unavailable (expected to become available on " + nearestAvailableDate + ")";
                } else {
                    bookStatus.value = "Unavailable (expected to become available on " + nextNearestAvailableDate + ")";
                }
            } else {
                bookStatus.value = "Unavailable (expected to become available on " + nearestAvailableDate + ")";
            }
        }
    }
}

function saveExistingReaderCard(id) {
    changeStatusOnExistingReaderCard(id);
    let readerCard = {
        bookId : readerCardRecord['bookId'],
        readerCardId : id,
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
    saveButton.style.display = 'inline-block';
    saveButton.setAttribute('onclick', 'editCreatedReaderCard(' + index + ')');
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
            readerCardId: 0,
            readerEmail: email.value,
            readerName: name.value,
            borrowDate: fullDate,
            timePeriod: timePeriodSelect.options[timePeriodSelect.selectedIndex].value,
            dueDate: fullDueDate,
            status: "borrowed",
            comment: comment.value
        }
        readerCards.set(index, readerCard);
        closeModal();
}

function changeStatusOnNewReaderCard() {
    if (availableAmount.value == 0) {
        if (!Boolean(nearestAvailableDate) || new Date(nearestAvailableDate) > new Date(editedReaderCardRecord.dueDate)) {
            bookStatus.value = "Unavailable (expected to become available on " + editedReaderCardRecord.dueDate + ")";
        } else {
            bookStatus.value = "Unavailable (expected to become available on " + getDateStringWithoutTime(new Date(nearestAvailableDate)) + ")";
        }
    } else {
        bookStatus.value = "Available " + availableAmount.value + " out of " + totalAmount.value;
    }
}

function saveNewReaderCard() {

    let form = document.getElementById("modal-form");
    let chk_status = form.checkValidity();
    form.reportValidity();
    if (chk_status) {
        document.getElementById("totalAmount").readOnly = true;
        document.getElementById("availableAmount").readOnly = true;
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
        let readerCard = readerCards.get(index);
        changeStatusOnExistingReaderCard(readerCard.readerCardId);
        readerCard.status = borrowingStatus.value;
        readerCard.comment = comment.value;
        readerCard.returnDate = returnDateValue;
        closeModal();
}



async function openClosedReaderCard(id) {
    openModal();
    let url = 'rdr-crd?id=' + id;
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
    let form = new FormData(document.getElementById("bookform"));
    let obj = [];
    readerCards.forEach((value, key)=>{
        obj.push(value);
    })
    console.log(obj);
    form.append("readerCards", JSON.stringify(obj));

    let url = 'lib-app?command=ADD_EDIT_BOOK';
    let response = await fetch(url, {
        method: 'POST',
        body: form
    });
    if (response.redirected) {
        window.location.href = response.url;
    }
}

async function getEmailsByPattern(pattern) {
    let autocomplete = document.getElementById('autocomplete');
    if (pattern.length >=3) {
        let url = "rdr?pattern=" + pattern.toLowerCase();
        let response = await fetch(url);
        let emails = await response.json();
        let vars = '';
        for (let i in emails) {
            vars += '<li onclick="autocompleteEmail(\'' + emails[i] + '\')">' + emails[i] + '</li>';
        }
        autocomplete.classList.remove("hidden");
        while (autocomplete.firstChild) {
            autocomplete.removeChild(autocomplete.firstChild);
        }
        autocomplete.insertAdjacentHTML('beforeend', '<ul>' + vars + '</ul>');
    } else {
        autocomplete.classList.add("hidden");
    }
}

async  function autocompleteEmail(email) {
    document.getElementById('email').value = email;
    let url = "rdr?email=" + email.toLowerCase();
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
    let min = totalBooks - availableBooks;
    if (+count <= min) {
        count = totalAmount.value = min;
    }
    availableAmount.value = availableBooks - (totalBooks - count);
    console.log(availableAmount.value);
}

