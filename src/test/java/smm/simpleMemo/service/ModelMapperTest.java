package smm.simpleMemo.service;

import smm.simpleMemo.domain.Memo;
import smm.simpleMemo.dto.MemoDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class ModelMapperTest {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void 테스트1() {
        MemoDto memoDto = new MemoDto();
        memoDto.setTitle("제목1");
        memoDto.setText("내용1");

        Memo memo = modelMapper.map(memoDto, Memo.class);
        System.out.println("제목 : " + memo.getTitle());
        System.out.println("내용 : " + memo.getText());

    }

}
