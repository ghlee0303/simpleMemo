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

    public Memo() {
    }

    public Memo(User writer, String title) {
        this.writer = writer;
        this.title = title;
    }

    public Memo(User writer, String title, String text) {
        this.writer = writer;
        this.text = text;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
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

    public LocalDateTime getRead_date() {
        return read_date;
    }

    public void setRead_date(LocalDateTime read_date) {
        this.read_date = read_date;
    }

    public LocalDateTime getMod_date() {
        return mod_date;
    }

    public void setMod_date(LocalDateTime mod_date) {
        this.mod_date = mod_date;
    }

    public void copyMemoDto(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
