window.onload = function () {
    eventBinding();
}

function loginProcess() {
    const serverUri = "http://localhost:8080/loginProc";
    const email = document.getElementById("email").value;
    const pwd = document.getElementById("pwd").value;
    const options = {
        method : 'POST',
        cache : 'no-cache',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ email, pwd })
    }

    fetch(serverUri, options)
        .then(res =>
            loginCheck(res)
        )
        .then(loginCode =>
            loginAlert(loginCode)
        );
}


function loginCheck(res) {
    return new Promise(function(resolve, reject) {
        if (res.status == 401) {
            resolve(res.json())
        }
        resolve(100);
    });
}

function loginAlert(code) {
    switch (code) {
        case 100 :
            location.href="/";
            break;
        case 600 :
            alert("알 수 없는 이유로 로그인이 안되고 있습니다.");
            break;
        case 601 :
            alert("아이디 또는 비밀번호가 맞지 않습니다.");
            break;
    }
}

function eventBinding() {
    document.getElementById("loginBtn").addEventListener("click", loginProcess);
}