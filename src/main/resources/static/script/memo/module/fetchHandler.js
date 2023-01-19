class MemoAPIError extends Error {
    constructor(res) {
        super(res.message);
        this.name = "MemoAPIError";
        this.res = res;
    }
}

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
        switch (httpsStatusClass(res.httpStatus)) {
            case 2:
                resolve(res.data);
                break;
            case 4:
                reject(new MemoAPIError(res));
                break;
        }
    });
}

/**
 * promise 내부의 promise에서 에러 처리 시 사용함
 */
function fetchErrorThrow(error) {
    throw error;
}

function httpsStatusClass(httpsStatus) {
    return Math.floor(httpsStatus/100);
}

export {toJsonPromise, httpStatusHandler, fetchErrorThrow};