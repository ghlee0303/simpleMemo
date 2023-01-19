package smm.simpleMemo.controller.memo;

import smm.simpleMemo.detail.UserDetail;
import smm.simpleMemo.dto.MemoDto;
import smm.simpleMemo.response.ResponseMemo;
import smm.simpleMemo.dto.TempMemoDto;
import smm.simpleMemo.service.MemoService;
import smm.simpleMemo.service.TempMemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RestMemoController {
    private final MemoService memoService;
    private final TempMemoService tempMemoService;

    public RestMemoController(MemoService memoService, TempMemoService tempMemoService) {
        this.memoService = memoService;
        this.tempMemoService = tempMemoService;
    }

    @ResponseBody
    @PutMapping("memo")
    public ResponseMemo<TempMemoDto> putMemoUpdate(@Valid @RequestBody MemoDto memoDto, Authentication authentication) {
        int userId = ((UserDetail) authentication.getPrincipal()).getUser().getId();
        TempMemoDto tempMemoDto = memoService.updateMemo(memoDto, userId);

        return new ResponseMemo<>(tempMemoDto);
    }

    @ResponseBody
    @DeleteMapping("memo")
    public ResponseEntity<String> deleteMemo(@RequestHeader("memoId") int memoId, Authentication authentication) {
        int userId = ((UserDetail) authentication.getPrincipal()).getUser().getId();
        memoService.deleteMemo(memoId, userId);

        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @GetMapping("memo")
    public ResponseMemo<MemoDto> getMemoData(@RequestParam(value = "id") int memoId, Authentication authentication) {
        int userId = ((UserDetail) authentication.getPrincipal()).getUser().getId();
        MemoDto memoDto = memoService.findById(memoId, userId);
        return new ResponseMemo<>(memoDto);
    }

    @ResponseBody
    @GetMapping("memo/list")
    public ResponseMemo<List<MemoDto>> getMemoList(Authentication authentication, @RequestParam(value = "page") int page) {
        page = page > 0 ? page-1 : 0;
        List<MemoDto> memoDtoList = memoService.findMemoPageList(
                ((UserDetail) authentication.getPrincipal()).getUser().getId()
                , page
        );
        return new ResponseMemo<>(memoDtoList);
    }

    @ResponseBody
    @GetMapping("memo/viewedList")
    public ResponseMemo<List<MemoDto>> getMemoViewedList(Authentication authentication) {
        List<MemoDto> memoDtoList = memoService.findViewedList(((UserDetail) authentication.getPrincipal()).getUser().getId());
        return new ResponseMemo<>(memoDtoList);
    }

    @ResponseBody
    @GetMapping("memo/latest")
    public ResponseMemo<MemoDto> getMemoLatest(Authentication authentication) {
        MemoDto memoDto = memoService.findLatest(((UserDetail) authentication.getPrincipal()).getUser().getId());
        return new ResponseMemo<>(memoDto);
    }

    @ResponseBody
    @GetMapping("memoTemp")
    public ResponseMemo<TempMemoDto> getMemoTempData(@RequestParam(value = "id") int tempMemoId) {
        TempMemoDto tempMemoDto = tempMemoService.findById(tempMemoId);
        return new ResponseMemo<>(tempMemoDto);
    }

    @ResponseBody
    @GetMapping("memoTemp/list")
    public ResponseMemo<List<TempMemoDto>> getMemoTempList(@RequestParam(value = "id") int tempMemoId) {
        List<TempMemoDto> tempMemoDtoList = tempMemoService.findByMemoId(tempMemoId);
        return new ResponseMemo<>(tempMemoDtoList);
    }
}
