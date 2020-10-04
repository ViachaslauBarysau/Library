function setNewReaderCardProperties() {
    email.readOnly = false;
    name.readOnly = false;
    timePeriodSelect.hidden = false;
    timePeriodSelect.disabled = false
    timePeriodLabel.hidden = false;
    borrowDateLabel.hidden = true;
    borrowDate.hidden = true;
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
    borrowDateLabel.hidden = false;
    borrowDate.hidden = false;
    borrowingStatus.hidden = false;
    borrowingStatusLabel.hidden = false;
    borrowingStatus.disabled = false;
    comment.readOnly = false;
}

function setCreatedReaderCardProperties() {
    email.readOnly = true;
    name.readOnly = true;
    timePeriodSelect.hidden = false;
    timePeriodLabel.hidden = false;
    timePeriodSelect.disabled = false;
    borrowDateLabel.hidden = false;
    borrowDate.hidden = false;
    borrowingStatusLabel.hidden = true;
    borrowingStatus.hidden = true;
    borrowingStatus.disabled = false;
    comment.readOnly = false;
}

function setArchiveReaderCardProperties() {
    email.readOnly = true;
    name.readOnly = true;
    timePeriodSelect.hidden = false;
    timePeriodLabel.hidden = false;
    timePeriodSelect.disabled = true;
    borrowDateLabel.hidden = false;
    borrowDate.hidden = false;
    borrowDate.readOnly = true;
    borrowingStatus.disabled = true;
    borrowingStatus.hidden = false;
    borrowingStatusLabel.hidden = false;
    comment.readOnly = true;

}