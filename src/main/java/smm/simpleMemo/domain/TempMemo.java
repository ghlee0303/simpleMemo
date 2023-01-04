package smm.simpleMemo.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "temp_memo")
public class TempMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 기본 메모
    @ManyToOne(targetEntity = Memo.class)
    @JoinColumn(name = "memo_id")
    private Memo memo;

    // 제목
    @Column(name = "title")
    private String title;

    // 내용
    @Column(name = "text")
    private String text;

    @Column(updatable = false)
    protected LocalDateTime reg_date;       //변경 날짜

    public TempMemo() {
    }

    public TempMemo(Memo memo, String title, String text) {
        this.memo = memo;
        this.title = title;
        this.text = text;
        this.reg_date = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Memo getMemo() {
        return memo;
    }

    public void setMemo(Memo memo) {
        this.memo = memo;
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
}

