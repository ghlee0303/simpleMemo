package smm.simpleMemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class MemoDto {
    Integer id;
    @JsonProperty("memoTitle")
    @NotBlank (message = "제목을 작성해주세요")
    private String title;
    @JsonProperty("memoText")
    @NotBlank (message = "내용을 작성해주세요")
    private String text;
    private LocalDateTime reg_date;
    private LocalDateTime mod_date;
    private LocalDateTime del_date;
    private LocalDateTime read_date;

    public MemoDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getReg_date() {
        return reg_date;
    }

    public void setReg_date(LocalDateTime reg_date) {
        this.reg_date = reg_date;
    }

    public LocalDateTime getMod_date() {
        return mod_date;
    }

    public void setMod_date(LocalDateTime mod_date) {
        this.mod_date = mod_date;
    }

    public LocalDateTime getDel_date() {
        return del_date;
    }

    public void setDel_date(LocalDateTime del_date) {
        this.del_date = del_date;
    }

    public LocalDateTime getRead_date() {
        return read_date;
    }

    public void setRead_date(LocalDateTime read_date) {
        this.read_date = read_date;
    }

    public boolean isEmpty() {
        if (this.id == null) {
            return true;
        }
        return false;
    }
}
