package smm.simpleMemo.response;

import java.time.LocalDateTime;

public class ErrorResult {
    private LocalDateTime errorTime;
    private String message;
    private int err_code;

    public ErrorResult() {
    }

    public ErrorResult(String message, int err_code) {
        this.message = message;
        this.err_code = err_code;
        this.errorTime = LocalDateTime.now();
    }

    public LocalDateTime getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(LocalDateTime errorTime) {
        this.errorTime = errorTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }
}
