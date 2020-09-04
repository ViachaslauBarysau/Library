function getDto(id) {
    return new Promise((resolve, reject)=> {
        fetch('lib-contr/v1/${id}')
            .then(response=>response.json())
            .then(post => resolve(post))
            .catch(err => reject(err));

    });
}


// document.getElementById('secondrow').value = "img";
// document.getElementById('thirdrow').value = "img";
// document.getElementById('fourthrow').value = "img";
// let i = new Object();
// i = '${bookpagedto}';
//
// let j = i[readerCards];
// document.getElementById('firstrow').value = j;
// // let k = j;''