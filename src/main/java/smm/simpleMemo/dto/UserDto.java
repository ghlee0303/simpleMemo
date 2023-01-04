package smm.simpleMemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
    private Integer id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("pwd")
    private String pwd;

    public UserDto() {
        this.email = null;
        this.pwd = null;
    }

    public UserDto(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
