import * as fetchHandler from 'fetchHandler';
import * as sidebar from 'sidebar';

const errorThrower = fetchHandler.fetchErrorThrow;
const toJsonPromise = fetchHandler.toJsonPromise;
const errorMessage = fetchHandler.errorMessage;
const httpStatusHandler = fetchHandler.httpStatusHandler;

const urlParams = new URLSearchParams(location.search);
const page = urlParams.has('p') ? urlParams.get('p') : 1;
const total = document.getElementById("total").innerText;

window.onload = function () {
    setPageIndex()
        .then(() => mainMemoListSetting())
        .then(() => sidebar.viewedMemoListSetting())
        .catch(err => errorMessage(err));
}

/**
 * 메모리스트 호출
 */
async function mainMemoListSetting() {
    const serverUri = "http://localhost:8080/memo/data/list?page=" + page;

    await fetch(serverUri)
        .then(res => toJsonPromise(res))
        .then(res => httpStatusHandler(res))
        .then(memoList => memoListDateSetting(memoList))
        .then(memoList => {
            memoList.map((memoData, line) => {
                insertMemoList(memoData, line);
            });
        })
        .catch(errorThrower);

    return true;
}

function getYmd(d) {
    //yyyy-mm-dd 포맷 날짜 생성
    return d.getFullYear()
        + "-" + ( (d.getMonth() + 1) > 9 ? (d.getMonth() + 1).toString() : "0" + (d.getMonth() + 1) )
        + "-" + ( d.getDate() > 9 ? d.getDate().toString() : "0" + d.getDate().toString() );
}
function getHm(t) {
    let h = ( t.getHours() > 9 ? t.getHours().toString() : "0" + t.getHours().toString() );
    let m = ( t.getMinutes() > 9 ? t.getMinutes().toString() : "0" + t.getMinutes().toString() );
    return h+":"+m;
}

function dateFormat(d, now) {
    if ( !( (d.getMonth() == now.getMonth()) && (d.getDate() == now.getDate()) ) ) {
        return getYmd(d);
    }
    return getHm(d);
}

function memoListDateFormat(memoData) {
    let now = new Date();
    let reg_date = new Date(memoData.reg_date);
    let mod_date = new Date(memoData.mod_date);
    let read_date = new Date(memoData.read_date);

    memoData.reg_date = dateFormat(reg_date, now);
    memoData.mod_date = dateFormat(mod_date, now);
    memoData.read_date = dateFormat(read_date, now);
}

function memoListDateSetting(mainMemoData) {
    return new Promise(function(resolve, reject) {
        if (Array.isArray(mainMemoData)) {
            mainMemoData.map((memoData) => {
                memoListDateFormat(memoData);
            });
        } else {
            memoListDateFormat(mainMemoData);
        }

        resolve(mainMemoData);
    });
}

function insertMemoList(memoData, line) {
    let tHeadList = ["id", "memoTitle", "reg_date", "read_date", "mod_date"];
    let tbody = document.getElementById("memoListTbody");
    let index = (page - 1) * 10 + line + 1;
    let tableRow = document.createElement('tr');
    tableRow.setAttribute('value', memoData.id);
    tableRow.addEventListener("click", () => {
       window.location.href = "/memo/" + memoData.id;
    });

    tHeadList.map(thead => {
        let tdInnerHtml = thead == "id" ? index : memoData[thead];
        let tableData = document.createElement('td');
        tableData.innerHTML = tdInnerHtml;
        tableRow.appendChild(tableData);
    });

    tbody.appendChild(tableRow);
}

function setPageIndex() {
    return new Promise(function(resolve, reject) {
        const pageMax = Math.floor((total-1)/10)+1;
        const pageNowMax = Math.floor((page/10)+1)*10;
        const pageNowMin = pageNowMax - 9;
        
        if (page > pageMax) {
            reject("잘못 된 페이지 입니다.");
        }

        const pageIndexBody = document.getElementById("page_index_wrap");

        for (let i=pageNowMin; i<=pageNowMax && i<=pageMax; i++) {
            let pageIndex = document.createElement('a');
            pageIndex.classList.add("page_atom");
            if (page == i) {
                pageIndex.classList.add("active");
            }
            pageIndex.href = "/memo/list?p=" + i;
            pageIndex.innerText = i;
            pageIndexBody.appendChild(pageIndex);
        }

        resolve(true);
    });
}

function listErrorHandler(error) {
    alert(error.message);
    location.href = "/memo/list";
}
