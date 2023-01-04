package smm.simpleMemo.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "temp_memo")
public class TempMemo extends BaseEntity {
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
    protected TempMemo() {
    }

    public TempMemo(Memo memo, String title, String text) {
        this.memo = memo;
        this.title = title;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public Memo getMemo() {
        return memo;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}

