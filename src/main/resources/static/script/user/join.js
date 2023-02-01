import * as fetchHandler from 'fetchHandler';

window.onload = function () {
    eventBinding();
    console.log("????");
}

function joinProcess() {
    const serverUri = "/join";
    const email = document.getElementById("email").value;
    const pwd = document.getElementById("pwd").value;
    console.log(email + " / " + pwd);
    console.log("이 뭐꼬 왜안대노");
    const options = {
        method : 'POST',
        cache : 'no-cache',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ email, pwd })
    }

    fetch(serverUri, options)
        .then((res) => fetchHandler.toJsonPromise(res))
        .then((res) => fetchHandler.httpStatusHandler(res) )
        .then((res) => {
            location.href = "/login";
        })
        .catch((err) => {
            alert(err.res.data.message);
        });
}

function eventBinding() {
    document.getElementById("joinBtn").addEventListener("click", joinProcess);
}