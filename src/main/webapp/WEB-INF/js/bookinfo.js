// function getDto(id) {
//     return new Promise((resolve, reject)=> {
//         fetch('lib-contr/v1/${id}')
//             .then(response=>response.json())
//             .then(post => resolve(post))
//             .catch(err => reject(err));
//
//     });
// }



function getReaderCard() {
    let obj;
    return new Promise((resolve, reject)=> {
        fetch('rdr-crd?id=1')
            .then(response => response.json())
            .then(data => obj = data)
            .then(() => console.log(obj))
            .catch(err => reject(err));
        // document.getElementById('firstrow').value = obj;
        // document.getElementById('thirdrow').value = "img";
        // document.getElementById('fourthrow').value = "img";

    });




}
function getEmails() {
    let obj;
    return new Promise((resolve, reject)=> {
        fetch('rdr?pattern=p')
            .then(response => response.json())
            .then(data => obj = data)
            .then(() => console.log(obj))
            .catch(err => reject(err));
        // document.getElementById('firstrow').value = obj;
        // document.getElementById('thirdrow').value = "img";
        // document.getElementById('fourthrow').value = "img";

    });




}



// let i = new Object();
// i = '${bookpagedto}';
// document.getElementById('secondrow').value = i;
// document.getElementById('thirdrow').value = "img";
// document.getElementById('fourthrow').value = "img";

