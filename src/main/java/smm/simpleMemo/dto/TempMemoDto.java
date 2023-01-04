package smm.simpleMemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class TempMemoDto {
    private Integer id;
    @JsonProperty("memoTitle")
    @NotBlank(message = "제목을 작성해주세요")
    private String title;
    @JsonProperty("memoText")
    @NotBlank (message = "내용을 작성해주세요")
    private String text;
    private LocalDateTime reg_date;

    public TempMemoDto() {
    }

    public TempMemoDto(String title, String text, LocalDateTime reg_date) {
        this.title = title;
        this.text = text;
        this.reg_date = reg_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getReg_date() {
        return reg_date;
    }

    public void setReg_date(LocalDateTime reg_date) {
        this.reg_date = reg_date;
    }
}
