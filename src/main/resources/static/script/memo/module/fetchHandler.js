/**
 * promise json 형태로 반환
 */
function toJsonPromise(res) {
    return new Promise(function(resolve, reject) {
        return resolve(res.json());
    });
}

/**
 * http status에 맞는 처리
 */
function httpStatusHandler(res) {
    return new Promise(function(resolve, reject) {
        switch (res.httpStatus) {
            case 200:
                resolve(res.data);
                break;
            case 404:
                reject(new Error(res.message))
                break;
        }
    });
}

/**
 * 에러처리
 * 최종 promise에서 내부 promise 에러 발생 시에 맞는 처리
 */
function errorMessage(error) {
    switch (error.name) {
        case "MemoAPIError":
            alert(error.message);
            location.href = "/";
            break;
        default:
            console.log(error);
    }
}

/**
 * 에러메시지 throw
 * promise 내부의 promise에서 에러 처리 시 사용함
 */
function fetchErrorThrow(error) {
    throw error;
}

export {toJsonPromise, httpStatusHandler, errorMessage, fetchErrorThrow};