package smm.simpleMemo.domain;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "memo")
@DynamicInsert
public class Memo extends BaseEntity {

    // 게시글번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 작성자
    @ManyToOne
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    private User writer;

    // 제목
    @Column(name = "title")
    private String title;

    // 내용
    @Column(name = "text")
    private String text;

    private LocalDateTime read_date;

    private LocalDateTime mod_date;

    protected Memo() {
    }

    /**
     * 새 메모 작성
     * mod_date(변경날짜)의 경우 새 메모 작성 시에도 동일하게 변경 됨
     * @param writer
     * @param title
     * @param mod_date
     */
    public Memo(User writer, String title, LocalDateTime mod_date) {
        this.writer = writer;
        this.title = title;
        this.mod_date = mod_date;
    }

    public Integer getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getRead_date() {
        return read_date;
    }

    public LocalDateTime getMod_date() {
        return mod_date;
    }

    public void setRead_date(LocalDateTime read_date) {
        this.read_date = read_date;
    }

    public void setMod_date(LocalDateTime mod_date) {
        this.mod_date = mod_date;
    }

    public void updateMemo(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
