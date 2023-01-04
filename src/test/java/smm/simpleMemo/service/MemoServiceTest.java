package smm.simpleMemo.service;

import smm.simpleMemo.dto.MemoDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemoServiceTest {

    @Autowired
    private MemoService memoService;
    @Autowired
    private TempMemoService tempMemoService;
    @Autowired
    private ModelMapper modelMapper;


    @Test
    void 테스트1() {
        MemoDto memoDto = new MemoDto();
        memoDto.setId(1);
        memoDto.setTitle("제목1");
        memoDto.setText("내용1");
    }


    @Test
    void 테스트4() {
        tempMemoService.findByMemoId(1).forEach(tempMemoDto -> {
            System.out.println(tempMemoDto.getTitle());
        });
    }

    @Test
    void 테스트5() {
        MemoDto memoDto = memoService.findLatest(1);
        System.out.println(memoDto.getTitle() + " / " + memoDto.getRead_date() + " / " + memoDto.getId());
    }

    @Test
    void 테스트6() {
        List<MemoDto> memoDtoList = memoService.findMemoPageList(1, 0);
        memoDtoList.forEach(tempMemoDto -> {
            System.out.println(tempMemoDto.getTitle());
        });
    }

    @Test
    void 테스트7() {
        MemoDto memoDto = memoService.findLatest(2);
        //MemoDto memoDto = new MemoDto();
        if (memoDto.getId() == null) {
            System.out.println("비었수다");
        }
    }

}
