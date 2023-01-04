package smm.simpleMemo.service;

import smm.simpleMemo.domain.Memo;
import smm.simpleMemo.domain.User;
import smm.simpleMemo.dto.MemoDto;
import smm.simpleMemo.dto.TempMemoDto;
import smm.simpleMemo.exception.NotFoundException;
import smm.simpleMemo.repository.MemoDslResp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
public class MemoService {
    @Autowired
    private TempMemoService tempMemoService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    private final MemoDslResp memoDslResp;

    public MemoService(MemoDslResp memoDslResp) {
        this.memoDslResp = memoDslResp;
    }

    private Memo findMemo(int memoId, int userId) {
        return memoDslResp.findByIdAndWriterOpt(memoId, userService.findUser(userId))
                .orElseThrow(()-> new NotFoundException("해당 메모를 찾을 수 없습니다.", 701));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public int newMemo(int userId) {
        User writer = userService.findUser(userId);

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        String parsedLocalDateTimeNow = localDateTimeNow.format(DateTimeFormatter.ofPattern("MM월 dd일 HH시"));
        String memoTitle = "새 메모 - " + parsedLocalDateTimeNow;

        Memo memo = new Memo(writer, memoTitle);
        memo.setMod_date(localDateTimeNow);
        memoDslResp.save(memo);

        return memo.getId();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public TempMemoDto saveMemo(MemoDto memoDto, int userId) {
        Memo memo = findMemo(memoDto.getId(), userId);
        TempMemoDto tempMemoDto = tempMemoService.saveTempMemo(memo);
        memo.copyMemoDto(
                memoDto.getTitle(),
                memoDto.getText()
        );
        memo.setRead_date(LocalDateTime.now());

        return tempMemoDto;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void deleteMemo(int memoId, int userId) {
        Memo memo = findMemo(memoId, userId);

        memo.setDel_date(LocalDateTime.now());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public MemoDto findById(int memoId, int userId) {
        Memo memo = findMemo(memoId, userId);
        memo.setRead_date(LocalDateTime.now());

        return modelMapper.map(memo, MemoDto.class);
    }

    public List<MemoDto> findMemoPageList(int userId, int page) {
        User writer = userService.findUser(userId);
        List<MemoDto> memoDtoList = new ArrayList<>();
        int offset = page * 10;

        memoDslResp.findMemoPageList(writer, offset, 10).forEach((memo) -> {
            MemoDto memoDto = modelMapper.map(memo, MemoDto.class);
            memoDto.setText(null);        // 리스트 호출 시 내용은 비공개
            memoDtoList.add(memoDto);
        });

        return memoDtoList;
    }

    public List<MemoDto> findViewedList(int userId) {
        User writer = userService.findUser(userId);
        List<Memo> memoList = memoDslResp.findViewedList(writer, 0, 10);
        List<MemoDto> memoDtoList = new ArrayList<>();

        memoList.forEach((memo) -> {
            memoDtoList.add(modelMapper.map(memo, MemoDto.class));
        });
        Collections.reverse(memoDtoList);

        return memoDtoList;
    }

    public MemoDto findLatest(int userId) {
        User writer = userService.findUser(userId);
        List<Memo> memo =  memoDslResp.findViewedList(writer, 0, 1);

        if (memo == null || memo.isEmpty()) {
            return new MemoDto();
        }

        return modelMapper.map(memo.get(0), MemoDto.class);
    }

    public Integer findWrittenCount(int userId) {
        User writer = userService.findUser(userId);
        return memoDslResp.findWrittenCount(writer);
    }
}
