package smm.simpleMemo.service;

import smm.simpleMemo.domain.Memo;
import smm.simpleMemo.domain.TempMemo;
import smm.simpleMemo.dto.TempMemoDto;
import smm.simpleMemo.exception.NotFoundException;
import smm.simpleMemo.repository.MemoDslResp;
import smm.simpleMemo.repository.TempMemoDslResp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class TempMemoService {
    @Autowired
    private ModelMapper modelMapper;
    private TempMemoDslResp tempMemoDslResp;
    private MemoDslResp memoDslResp;

    public TempMemoService(TempMemoDslResp tempMemoDslResp, MemoDslResp memoDslResp) {
        this.tempMemoDslResp = tempMemoDslResp;
        this.memoDslResp = memoDslResp;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public TempMemoDto saveTempMemo(Memo memo) {

        TempMemo tempMemo = new TempMemo(
                memo,
                memo.getTitle(),
                memo.getText()
        );
        tempMemoDslResp.save(tempMemo);

        return modelMapper.map(tempMemo, TempMemoDto.class);
    }

    public TempMemoDto findById(int tempMemoId) {
        TempMemo tempMemo = tempMemoDslResp.findByIdOpt(tempMemoId)
                .orElseThrow(() -> new NotFoundException("해당 이전 메모를 찾을 수 없습니다.", 601));

        return modelMapper.map(tempMemo, TempMemoDto.class);
    }

    public List<TempMemoDto> findByMemoId(int memoId) {
        Memo memo = memoDslResp.findByIdOpt(memoId)
                .orElseThrow(()-> new NotFoundException("해당 메모를 찾을 수 없습니다.", 701));

        List<TempMemoDto> tempMemoDtoList = new ArrayList<>();
        tempMemoDslResp.findByMemoList(memo).forEach(tempMemo -> {
            tempMemoDtoList.add(modelMapper.map(tempMemo, TempMemoDto.class));
        });

        return tempMemoDtoList;
    }


}
