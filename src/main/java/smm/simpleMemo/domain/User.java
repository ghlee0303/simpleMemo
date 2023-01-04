package smm.simpleMemo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@ToString(of = {"id", "email"})
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "password")
    private String pwd;
    @Column(name = "email")
    private String email;

    protected User() {
    }

    public User(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    public String getEmail() {
        return email;
    }
}
