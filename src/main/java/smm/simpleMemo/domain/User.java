package smm.simpleMemo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@ToString(of = {"id", "email", "phone"})
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "password")
    private String pwd;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

    public User() {
    }

    public User(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
