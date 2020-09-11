const readerCardId = document.getElementById('readerCardId');
const readerId = document.getElementById('readerId');
const email = document.getElementById('email');
const name = document.getElementById('name');
const borrowDate = document.getElementById('borrowdate');
const timePeriodLabel = document.getElementById('timeperiodlabel');
const timePeriodSelect = document.getElementById('timeperiod');
const  borrowingStatus = document.getElementById('borrowingStatus');
const borrowingStatusLabel = document.getElementById('statuslabel');
const availableAmount = document.getElementById('availableAmount');
const totalAmount = document.getElementById('totalAmount');
const bookStatus = document.getElementById('bookstatus');
const comment = document.getElementById('comment');
const nearestAvailableDate = '${bookpagedto.nearestAvailableDate}';
const nextNearestAvailableDate = '${bookpagedto.nextNearestAvailableDate}';
const nearestAvailableDateID = '${bookpagedto.nearestAvailableDateID}';
let readerCardDtoId = -1;
let returnDateValue;

// function dismissModal() {
//     email.value = "";
//     name.value = "";
//     borrowDate.value = "";
//     readerCardId.value = 0;
// }

function openReaderCard(id) {
    if (id == 0 || isNaN (id)) {
        openNewReaderCard();
    } else {
        openExistingReaderCard(id);
    }
}

async function openExistingReaderCard(id) {
    let url = 'rdr-crd?id=' + id;
    let response = await fetch(url);
    let readerCard = await response.json();
    borrowingStatus.value = readerCard.status;
    console.log(readerCard);
    readerCardId.value = id;
    readerId.value = readerCard.readerId;
    email.value = readerCard.readerEmail;
    email.readOnly = true;
    name.value = readerCard.readerName;
    name.readOnly = true;
    borrowDate.value = readerCard.borrowDate;
    comment.value = readerCard.comment;
    timePeriodSelect.hidden = true;
    timePeriodLabel.hidden = true;
    borrowingStatus.hidden = false;
    borrowingStatusLabel.hidden = false;

}

function openNewReaderCard() {
    readerCardId.value = 0;
    readerId.value = 0;
    email.value = "";
    email.readOnly = false;
    name.value = "";
    name.readOnly = false;
    comment.value = "";
    let dueDate = new Date();
    let fullDueDate = getDateStringWithoutTime(dueDate);
    borrowDate.value = fullDueDate ;
    timePeriodLabel.hidden = false;
    timePeriodSelect.hidden = false;
    borrowingStatus.hidden = true;
    borrowingStatusLabel.hidden = true;
}

function saveReaderCard(){
    if (readerCardId.value == 0) {
        readerCardDtoId = 0;
        saveNewReaderCard();
    } else {
        readerCardDtoId = readerCardId.value;
        saveExistingReaderCard();
    }
}

function saveExistingReaderCard() {
    let returnDate = new Date();
    returnDateValue = getDateStringWithTime(returnDate);
    if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "returned") {
        document.getElementById(readerCardId.value).innerText = getDateStringWithTime(returnDate);
        availableAmount.value = availableAmount.value - 0 + 1;
        bookStatus.value = "Available " + availableAmount.value + " out of " + totalAmount.value;
    } else if (borrowingStatus.options[borrowingStatus.selectedIndex].value == "damaged" ||
        borrowingStatus.options[borrowingStatus.selectedIndex].value == "lost") {
        document.getElementById(readerCardId.value).innerText = getDateStringWithTime(returnDate);
        totalAmount.value -= 1;
        if (availableAmount.value > 0) {
            bookStatus.value = "Available " + totalAmount.value +" out of " + totalAmount.value;

        } else if (availableAmount.value == 0) {
            if (totalAmount.value == 0) {
                bookStatus.value = "Unavailable";
            } else if (readerCardId.value == nearestAvailableDateID) {
                bookStatus.value = "Unavailable (expected to become available on " + nextNearestAvailableDate + ")";
            }
        }

    }
}

function saveNewReaderCard() {
    availableAmount.value -= 1;

    const table = document.getElementById("table");
    const tr = document.createElement("tr");
    table.appendChild(tr);

    const td1 = document.createElement("td");
    td1.innerText=email.value;
    const td2 = document.createElement("td");
    td2.innerText=name.value;
    const td3 = document.createElement("td");
    let today = new Date();
    let fullDate = getDateStringWithoutTime(today);
    td3.innerText = fullDate;

    let dueDate = new Date();
    let timePeriod = + timePeriodSelect.options[timePeriodSelect.selectedIndex].value;
    dueDate.setMonth(dueDate.getMonth() + timePeriod);
    let fullDueDate = getDateStringWithoutTime(dueDate);
    const td4 = document.createElement("td");
    td4.innerText = fullDueDate;
    const td5 = document.createElement("td");

    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);
    tr.appendChild(td5);

    if (availableAmount.value == 0) {
        if (nearestAvailableDate.length == 0 || new Date(nearestAvailableDate) > dueDate){
            bookStatus.value = "Unavailable (expected to become available on " + fullDueDate + ")";
        } else {
            bookStatus.value = "Unavailable (expected to become available on " + getDateStringWithoutTime(new Date(nearestAvailableDate)) + ")";
        }
    } else {
        bookStatus.value = "Available " + availableAmount.value +" out of " + totalAmount.value;
    }

}

function getDateStringWithoutTime(date) {
    return date.getFullYear() + "-" + (date.getMonth()<9?('0'+(date.getMonth()+1)):(date.getMonth()+1))
        + "-" + (date.getDate()<10?('0'+date.getDate()):(date.getDate()));
}

function getDateStringWithTime(date) {
    return getDateStringWithoutTime(date) + " " + (date.getHours()<10?('0'+date.getHours()):(date.getHours())) +
        ":" + (date.getMinutes()<10?('0'+date.getMinutes()):(date.getMinutes()))
         + ":" + (date.getSeconds()<10?('0'+date.getSeconds()):(date.getSeconds()));
}

async function sendForm() {
    let form = new FormData(document.getElementById("bookform"));
    form.append("readerCardId", "" + readerCardDtoId);
    form.append("returnDate", returnDateValue);
    form.append("comment", comment.value);
    let url = 'lib-app?command=ADD_EDIT_BOOK';
    let response = await fetch(url, {method: 'POST',
        body: form});
    if (response.redirected) {
        window.location.href = response.url;
    }
}

function getEmails() {
    let obj;
    return new Promise((resolve, reject)=> {
        fetch('rdr?pattern=p')
            .then(response => response.json())
            .then(data => {return obj = data})
            .then(() => console.log(obj))
            .catch(err => reject(err));
        document.getElementById('firstrow').value = obj;

    });

}


