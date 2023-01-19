import * as fetchHandler from 'fetchHandler';

const errorThrower = fetchHandler.fetchErrorThrow;
const tempMemoId = window.location.pathname.split("/")[2];

window.onload = function () {
    tempMemoSetting()
        .then((res) => insertTempMemoData(res))
        .catch((err) => {
            console.log(err.res);
        })
}
/**
 * 이전 메모 호출
 */
async function tempMemoSetting() {
    const serverUri = "http://localhost:8080/memoTemp?id=" + tempMemoId;
    let tempMemoData;

    await fetch(serverUri)
        .then(res => fetchHandler.toJsonPromise(res))
        .then(res => fetchHandler.httpStatusHandler(res))
        .then(res =>  {
            tempMemoData = res;
        })
        .catch(errorThrower);

    return tempMemoData;
}

function insertTempMemoData(tempMemoData) {
    return new Promise(function(resolve, reject) {
        document.getElementById("title").innerHTML = tempMemoData.memoTitle;
        if (tempMemoData.memoText) {
            document.getElementById("text").innerHTML = tempMemoData.memoText;
        }

        return resolve();
    });

}