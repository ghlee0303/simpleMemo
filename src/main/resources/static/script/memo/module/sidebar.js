import * as fetchHandler from 'fetchHandler';

const errorThrower = fetchHandler.fetchErrorThrow;
/**
 * 열람한 메모리스트 호출
 */
async function viewedMemoListSetting() {
    const serverUri = "/memo/viewedList";

    await fetch(serverUri)
        .then(res => fetchHandler.toJsonPromise(res))
        .then(res => fetchHandler.httpStatusHandler(res))
        .then(viewedMemoList => {
            viewedMemoList.map(memoData => insertSidebarMemoList(memoData, "viewedMemoList"));
            return viewedMemoList;
        })
        .catch(errorThrower);

    return true;
}

/**
 * 이전 메모리스트 호출
 */
async function tempMemoListSetting(memoId) {
    const serverUri = "/memoTemp/list?id=" + memoId;

    await fetch(serverUri)
        .then(res => fetchHandler.toJsonPromise(res))
        .then(res => fetchHandler.httpStatusHandler(res))
        .then(tempMemoList => {
            tempMemoList.map(memoData => insertSidebarMemoList(memoData, "tempMemoList"));
            return tempMemoList;
        })
        .catch(errorThrower);
    return true;
}

function insertSidebarMemoList(memo, target) {
    let newElement = document.createElement('li');

    let newLink = document.createElement('a');
    newLink.setAttribute('value', memo.id);
    newLink.innerHTML = memo.memoTitle;

    newElement.appendChild(newLink);

    let targetHtml = document.getElementById(target);
    targetHtml.insertBefore(newElement, targetHtml.firstChild);

    switch (target) {
        case 'viewedMemoList':
            newElement.addEventListener('click', function (clicked) {
                let mainMemoId = clicked.target.getAttribute("value");
                location.href = '/memo/' + mainMemoId;
            });
            break;

        case 'tempMemoList':
            newElement.addEventListener('click', function (clicked) {
                let tempMemoId = clicked.target.getAttribute("value");
                window.open('/memoTemp/' + tempMemoId, 'popup', 'width=800,height=600');
            });
            break;
    }
}

export {viewedMemoListSetting, tempMemoListSetting, insertSidebarMemoList};