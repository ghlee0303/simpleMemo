import * as fetchHandler from 'fetchHandler';

window.onload = function () {
    eventBinding();
}

function loginProcess() {
    const serverUri = "/loginProc";
    const email = document.getElementById("email").value;
    const pwd = document.getElementById("pwd").value;
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
            location.href = "/";
        })
        .catch((err) => {
            alert(err.res.data.message);
        });
}

function eventBinding() {
    document.getElementById("loginBtn").addEventListener("click", loginProcess);
}