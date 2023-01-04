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
    private final UserDetailService userDetailService;

    public MemoController(MemoService memoService, UserDetailService userDetailService) {
        this.memoService = memoService;
        this.userDetailService = userDetailService;
    }

    @GetMapping("/")
    public String getIndex(Authentication authentication) {
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

    @GetMapping("memo/list")
    public String getMemoList(Authentication authentication, Model model) {
        Integer memoTotalCount = memoService.findWrittenCount(((UserDetail) authentication.getPrincipal()).getUser().getId());
        model.addAttribute("total", memoTotalCount);

        return "memo/memoList";
    }

    @GetMapping("memo/{id}")
    public String getMemoMain(Model model) {

        return "memo/memoMain";
    }

    @GetMapping("memoTemp")
    public String getMemoTemp(Model model) {

        return "memo/memoTemp";
    }

}
