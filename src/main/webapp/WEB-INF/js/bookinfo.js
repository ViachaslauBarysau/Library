let savePressedMarker = 0;
let readerCardRecord = {};
let editedReaderCardRecord = {'id' : -1};
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
const addButton = document.getElementById('addButton');
let returnDateValue;

if (availableBooks == 0) {
    addButton.hidden = true;
}

function setNewReaderCardProperties() {
    email.readOnly = false;
    name.readOnly = false;
    timePeriodSelect.hidden = false;
    timePeriodLabel.hidden = false;
    borrowingStatus.hidden = true;
    borrowingStatusLabel.hidden = true;
    borrowingStatus.disabled = false;
    comment.readOnly = false;

}

function setExistingReaderCardProperties() {
    email.readOnly = true;
    name.readOnly = true;
    timePeriodSelect.hidden = true;
    timePeriodLabel.hidden = true;
    borrowingStatus.hidden = false;
    borrowingStatusLabel.hidden = false;
    borrowingStatus.disabled = false;
    comment.readOnly = false;
}

function setReaderCard() {
    editedReaderCardRecord.id = readerCardId.value;
    editedReaderCardRecord.bookId = readerCardRecord['bookId'];
    editedReaderCardRecord.readerId = readerId.value;
    editedReaderCardRecord.readerEmail = email.value;
    editedReaderCardRecord.readerName = name.value;
    editedReaderCardRecord.status = borrowingStatus.options[borrowingStatus.selectedIndex].value;
    editedReaderCardRecord.timePeriod = timePeriodSelect.options[timePeriodSelect.selectedIndex].value;
    editedReaderCardRecord.returnDate = returnDateValue;
    editedReaderCardRecord.comment = comment.value;
}

function openModal() {
    let body = document.body;
    body.classList.add('modal-open');
    body.setAttribute('style', "display:block; padding-right: 17px;");
    body.setAttribute('position', "fixed");
    body.setAttribute('overflow', "hidden");


    let myModal = document.getElementById("myModal");
    myModal.classList.add('show');
    myModal.setAttribute('style', "display:block; padding-right: 17px;");
    myModal.removeAttribute('aria-hidden');

    let modalBackground = document.getElementById("modalbackground");
    modalBackground.classList.add("modal-backdrop", "fade", "show");
    modalBackground.setAttribute("opacity", ".5");
}

function closeModal() {
    let body = document.body;
    body.classList.remove('modal-open');
    body.removeAttribute("style");
    body.removeAttribute('overflow');
    body.removeAttribute('position');

    let myModal = document.getElementById("myModal");
    myModal.classList.remove('show');
    myModal.setAttribute('style', 'display: none;');
    myModal.setAttribute('aria-hidden', 'true');

    let modalBackground = document.getElementById("modalbackground");
    modalBackground.classList.remove("modal-backdrop", "fade", "show");
    modalBackground.setAttribute("opacity", "0");
}

async function openExistingReaderCard(id) {

    // if (savePressedMarker == 0) {
    //     saveButton.style.display = 'inline-block';
    // } else {
    //     saveButton.style.display = 'none';
    // }

    openModal();

    let url = 'rdr-crd?id=' + id;
    let response = await fetch(url);
    readerCardRecord = await response.json();

    setExistingReaderCardProperties();

    readerCardId.value = id;
    readerId.value = readerCardRecord.readerId;
    email.value = readerCardRecord.readerEmail;
    name.value = readerCardRecord.readerName;
    borrowDate.value = readerCardRecord.borrowDate;
    comment.value = readerCardRecord.comment;
    borrowingStatus.value = readerCardRecord.status;


}

// document.addEventListener('click', function(event) {
//
//     let modal = document.getElementById('myModal');
//     if (!modal.contains(event.target)) {
//         document.getElementById("modalbackground").classList.remove("modal-backdrop", "fade", "show");
//         document.getElementById("modalbackground").setAttribute("opacity", "0");
//         document.getElementById("myModal").classList.remove('show');
//         document.getElementById("myModal").setAttribute('style', 'display: none;');
//         document.getElementById("myModal").setAttribute('aria-hidden', 'true');
//         document.body.classList.remove('modal-open');
//         document.body.removeAttribute("style");
//     }
//
// });

function openNewReaderCard() {
    // saveButton.style.display = 'inline-block';
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
    borrowingStatus.value = "borrowed";

}

function opedEditedReaderCard() {

    // saveButton.style.display = 'inline-block';
    openModal();
    readerCardId.value = editedReaderCardRecord.id;
    readerId.value = editedReaderCardRecord.readerId;
    email.value = editedReaderCardRecord.readerEmail;
    name.value = editedReaderCardRecord.readerName;
    borrowDate.value = editedReaderCardRecord.borrowDate;
    borrowingStatus.value = editedReaderCardRecord.status;
    comment.value = editedReaderCardRecord.comment;

    if (readerCardId.value == 0) {
        setNewReaderCardProperties();
    } else {
        setExistingReaderCardProperties();
    }

}
document.getElementById('modal-form').onsubmit = saveReaderCard
function saveReaderCard(e) {
    e.preventDefault()
    var ele = document.getElementById("modal-form");
    var chk_status = ele.checkValidity();
    ele.reportValidity();
    if (chk_status) {
        console.log('valid')
        document.getElementById("totalAmount").readOnly = true;
        document.getElementById("availableAmount").readOnly = true;
        // savePressedMarker = 1;

        // addButton.hidden = true;

        if (readerCardId.value == 0) {
            saveNewReaderCard();
        } else {
            saveExistingReaderCard();
        }

        closeModal();
    }

}

function changeStatusOnExistingReaderCard () {

    let returnDate = new Date();
    if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "returned") {
        document.getElementById("rd" + readerCardId.value).innerText = getDateStringWithTime(returnDate);
        returnDateValue = getDateStringWithTime(returnDate);
        totalAmount.value = totalBooks;
        availableAmount.value = availableBooks - 0 + 1;
        bookStatus.value = "Available " + availableAmount.value + " out of " + totalAmount.value;
    } else if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "damaged" ||
        borrowingStatus.options[borrowingStatus.selectedIndex].value == "lost") {
        document.getElementById("rd" + readerCardId.value).innerText = getDateStringWithTime(returnDate);
        returnDateValue = getDateStringWithTime(returnDate);
        totalAmount.value = totalBooks - 1;
        availableAmount.value = availableBooks;
        setBookStatus();
    } else {
        totalAmount.value = totalBooks;
        availableAmount.value = availableBooks;
        returnDateValue = "";
        document.getElementById("rd" + readerCardId.value).innerText = "";
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


function saveExistingReaderCard() {

    changeStatusOnExistingReaderCard ();
    editedReaderCardRecord.borrowDate = readerCardRecord['borrowDate']
    editedReaderCardRecord.dueDate = readerCardRecord['dueDate'];
    setReaderCard();
    document.getElementById("link" + editedReaderCardRecord.id).
    setAttribute('onclick', 'opedEditedReaderCard()');
    saveButton.setAttribute('onclick', 'saveEditedReaderCard()');

}

function saveEditedReaderCard() {
    setReaderCard();

    if(readerCardId.value == 0) {

        document.getElementById('tdEmail').innerHTML = '<a href="#" id="link0"' +
            ' onclick="opedEditedReaderCard()" data-toggle="modal" data-target="#myModal"' +
            ' class="stretched-link">' + editedReaderCardRecord.readerEmail + '</a>';

        document.getElementById('tdName').innerText = editedReaderCardRecord.readerName;

        let today = new Date();
        let fullDate = getDateStringWithoutTime(today);
        editedReaderCardRecord.borrowDate = fullDate;
        document.getElementById('tdBorrowDate').innerText = fullDate;

        let dueDate = new Date();
        let timePeriod = + timePeriodSelect.options[timePeriodSelect.selectedIndex].value;
        dueDate.setMonth(dueDate.getMonth() + timePeriod);
        let fullDueDate = getDateStringWithoutTime(dueDate);
        editedReaderCardRecord.dueDate = fullDueDate;
        document.getElementById('tdDueDate').innerText = fullDueDate;

        changeStatusOnNewReaderCard();
    } else {

        changeStatusOnExistingReaderCard ();

    }
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

    setReaderCard();
    const table = document.getElementById("table");
    const tr = document.createElement("tr");
    table.appendChild(tr);
    const td1 = document.createElement("td");
    td1.id = "tdEmail";
    td1.innerText

    const td2 = document.createElement("td");
    td2.id = "tdName";

    const td3 = document.createElement("td");
    td3.id = "tdBorrowDate";

    const td4 = document.createElement("td");
    td4.id = "tdDueDate";

    const td5 = document.createElement("td");
    td5.id = "tdReturnDate";

    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);
    tr.appendChild(td5);
    // let tableRow = `<tr>
    //     <td>
    //         <a href="#" onclick="opedEditedReaderCard()" data-toggle="modal" data-target="#myModal" class="stretched-link">dirtyelegance8@gmail.com</a>
    //     </td>
    //     <td id="tdName">Viachaslau Barysau</td>
    //     <td id="tdBorrowDate">2020-09-20</td>
    //     <td id="tdDueDate">2020-10-20</td>
    //     <td id="tdReturnDate"></td>
    // </tr>`

    availableAmount.value = availableBooks - 1;

    saveEditedReaderCard();

    document.getElementById("link" + editedReaderCardRecord.id).
    setAttribute('onclick', 'opedEditedReaderCard()');
    saveButton.setAttribute('onclick', 'saveEditedReaderCard()');

}

function getDateStringWithoutTime(date) {
    return date.getFullYear() + "-" + (date.getMonth() < 9 ? ('0' + (date.getMonth() + 1)) : (date.getMonth() + 1))
        + "-" + (date.getDate() < 10 ? ('0' + date.getDate()) : (date.getDate()));
}

function getDateStringWithTime(date) {
    return getDateStringWithoutTime(date) + " " + (date.getHours() < 10 ? ('0' + date.getHours()) : (date.getHours())) +
        ":" + (date.getMinutes() < 10 ? ('0' + date.getMinutes()) : (date.getMinutes()))
        + ":" + (date.getSeconds() < 10 ? ('0' + date.getSeconds()) : (date.getSeconds()));
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

    timePeriodSelect.hidden = true;
    timePeriodLabel.hidden = true;

    // saveButton.style.display = 'none';

}



async function sendForm() {
    let form = new FormData(document.getElementById("bookform"));
    form.append("readerCardId", editedReaderCardRecord.id);
    form.append("readerId", editedReaderCardRecord.readerId);
    form.append("readerEmail", editedReaderCardRecord.readerEmail);
    form.append("readerName", editedReaderCardRecord.readerName);
    form.append("borrowDate", editedReaderCardRecord.borrowDate);
    form.append("dueDate", editedReaderCardRecord.dueDate);
    form.append("timePeriod", editedReaderCardRecord.timePeriod)
    form.append("returnDate", editedReaderCardRecord.returnDate);
    form.append("status", editedReaderCardRecord.status);
    form.append("comment", editedReaderCardRecord.comment);

    form.append("borrowList", JSON.stringify([{name: "ivan"}, {name: "vasily"}]));

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
        console.log(pattern);
        let url = "rdr?pattern=" + pattern.toLowerCase();
        let response = await fetch(url);
        let emails = await response.json();
        console.log(emails);
        let vars = ''
        for (let i in emails) {
            console.log(emails[i])
            vars += '<li onclick="autocompleteEmail(\'' + emails[i] + '\')">' + emails[i] + '</li>'
        }
        console.log(vars)
        autocomplete.classList.remove("hidden");
        while (autocomplete.firstChild) {
            autocomplete.removeChild(autocomplete.firstChild);
        }
        autocomplete.insertAdjacentHTML('beforeend', '<ul>' + vars + '</ul>');
    } else {
        autocomplete.classList.add("hidden");
    }
}

function autocompleteEmail(i) {
    document.getElementById('email').value = i
    closeAutocomplete()
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

