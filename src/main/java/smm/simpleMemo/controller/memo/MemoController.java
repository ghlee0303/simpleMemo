package smm.simpleMemo.controller.memo;

import smm.simpleMemo.detail.UserDetail;
import smm.simpleMemo.dto.MemoDto;
import smm.simpleMemo.service.MemoService;
import smm.simpleMemo.service.UserDetailService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemoController {
    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/")
    public String getLatestMemo(Authentication authentication) {
        MemoDto memoDto = memoService.findLatest(((UserDetail) authentication.getPrincipal()).getUser().getId());
        if (memoDto.isEmpty()) {
            return "redirect:/memo/new";
        }

        return "redirect:/memo/" + memoDto.getId();
    }

    @GetMapping("memo/new")
    public String getMemoNew(Authentication authentication) {
        int memoId = memoService.newMemo(((UserDetail) authentication.getPrincipal()).getUser().getId());

        return "redirect:/memo/" + memoId;
    }

    @GetMapping("memo/page")
    public String getMemoList(Authentication authentication, Model model) {
        Integer memoTotalCount = memoService.findWrittenCount(((UserDetail) authentication.getPrincipal()).getUser().getId());
        model.addAttribute("total", memoTotalCount);

        return "memo/memoList";
    }

    @GetMapping("memo/{id}")
    public String getMemoMain(Model model) {

        return "memo/memoMain";
    }

    @GetMapping("memoTemp/{id}")
    public String getMemoTemp(Model model) {

        return "memo/memoTemp";
    }
}
