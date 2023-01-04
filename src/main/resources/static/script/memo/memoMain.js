import * as fetchHandler from 'fetchHandler';
import * as sidebar from 'sidebar';

let errorThrower = fetchHandler.fetchErrorThrow;
let toJsonPromise = fetchHandler.toJsonPromise;
let errorMessage = fetchHandler.errorMessage;
let httpStatusHandler = fetchHandler.httpStatusHandler;
const memoId = window.location.pathname.split("/")[2];
let changeTimerId;

window.onload = function () {
    mainMemoSetting()
        .then(() => sidebar.viewedMemoListSetting())
        .then(() => sidebar.tempMemoListSetting(memoId))
        .catch(err => errorMessage(err));
    htmlEditorSetting();
    eventBinding();
}

function eventBinding() {
    document.querySelector("#writeButton").addEventListener("click", manualSaveMemoHtml);
    document.querySelector("#deleteButton").addEventListener("click", deleteMemoHtml);
}

/**
 * 주 메모 호출
 */
async function mainMemoSetting() {
    const serverUri = "/memo/data?id=" + memoId;

    await fetch(serverUri)
        .then(res => toJsonPromise(res))
        .then(res => httpStatusHandler(res))
        .then(res =>  {
            insertMainMemoData(res);
            return res;
        })
        .then(() => { changeEventBinding();})
        .catch(errorThrower);

    return true;
}

/**
 * 작성한 html 저장
 */
function saveMemoHtml() {
    const memoUrl = "/memo/save";
    const memoTitle = document.querySelector("#memoTitle").value;
    const memoText = document.querySelector("#htmlEditor").value;

    if (!memoTitle || !memoText) {
        alert("제목 및 내용을 작성해주세요");
        return;
    }

    const options = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            id: memoId,
            memoTitle: memoTitle,
            memoText: memoText,
        })
    }

    fetch(memoUrl, options)
        .then(res => toJsonPromise(res))
        .then(res => {
            return httpStatusHandler(res).catch(errorThrower);
        })
        .then(tempMemo => {
            //console.log(tempMemo);
            insertMemoList(tempMemo, "tempMemoList");
            return tempMemo;
        })
        .then(res => { viewedMemoListSameTitleChange(memoTitle); })
        .then(res => { saveOkPopup(); })
        .catch(err => errorMessage(err));
}

/**
 * 작성한 html 삭제 (del_date 추가)
 */
function deleteMemoHtml() {
    const serverUri = "http://localhost:8080/memo/delete?id=" + memoId;

    fetch(serverUri)
        .then(res => {
                alert("성공적으로 삭제 되었습니다.");
                location.href = "http://localhost:8080/memo/list";
            }
        );
}
// 삭제시 발생하는 에러 처리

/**
 * 이미지 파일 업로드
 */
function imageUpload(file, editor) {
    const serverUri = "http://localhost:8080/image/upload";
    let formData = new FormData();
    formData.append("image", file);

    const options = {
        method : 'POST',
        cache : 'no-cache',
        body: formData
    }

    fetch(serverUri, options)
        .then(res => toJsonPromise(res))
        .then(res => httpStatusHandler(res))
        .then(res => { htmlEditorImageUrlWrite(res); })
        .catch(errorThrower);
}

function insertMainMemoData(memoData) {
    document.getElementById("memoTitle").value = memoData.memoTitle;
    if (memoData.memoText) {
        $("#htmlEditor").summernote('editor.pasteHTML', memoData.memoText);
    }
}

/**
 * html 에디터 설정
 */
function htmlEditorSetting() {
    $('#htmlEditor').summernote({
        height: 700,                 // 에디터 높이
        minHeight: 700,             // 최소 높이
        maxHeight: 700,             // 최대 높이
        width: 900,
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['color', ['color']],
            ['para', ['paragraph']],
            ['table', ['table']],
            ['insert', ['link', 'picture', 'video']]
        ],
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",					// 한글 설정
        placeholder: '',	//placeholder 설정
        callbacks: {	// 추가적인 설정
            onImageUpload : function(files) {       // 이미지 업로드 부분
                imageUpload(files[0],this);
            }
        }
    });
}

/**
 * 저장한 이미지 uri 쓰기
 */
function htmlEditorImageUrlWrite(res) {
    console.log(res.fullPath);
    $('#htmlEditor').summernote('insertImage', res.fullPath);
}

/**
 * 작성중인 메모 변경 감지 이벤트 할당
 */
function changeEventBinding() {
    // 제목 변경 감지
    $("#memoTitle").on("propertychange change keyup paste input", function() {
        changedSaveMemoHtml();
    });
    // 내용 변경 감지
    $('#htmlEditor').on('summernote.change', function(we, contents, $editable) {
        changedSaveMemoHtml();
    });
}

/**
 * 작성중인 메모 변경 감지 시 자동 저장
 */
function changedSaveMemoHtml() {
    if (changeTimerId) {
        clearTimeout(changeTimerId);
    }

    changeTimerId = setTimeout(saveMemoHtml, 2500);     // 2.5초 후 저장
}

/**
 * 저장 후 열람한 메모 목록에서 현재 메모와 같은 메모 제목을 변경
 */
function viewedMemoListSameTitleChange(viewedTitle) {
    let selector = "[value='"+memoId+"']";
    const container = document.getElementById("viewedMemoList");
    const matches = container.querySelector(selector);
    matches.innerText = viewedTitle;
}

/**
 * 수동 자동 저장
 */
function manualSaveMemoHtml() {
    if (changeTimerId) {
        clearTimeout(changeTimerId);
    }

    saveMemoHtml();
}

/**
 * 저장 완료 시 팝업
 */
function saveOkPopup() {
    let container = document.getElementById('popup-container');
    let popup = document.createElement('div');
    popup.innerHTML = '저장 완료!';
    popup.id = "saveSuccess";
    popup.classList.add("alert", "alert-success");
    container.appendChild(popup);
    setTimeout(function() {
        popup.style.display = 'block';
        setTimeout(function() {
            popup.parentNode.removeChild(popup);
        }, 2000);
    }, 0);
}
/**/