package smm.simpleMemo.exception;

public class MemoAPIException extends RuntimeException{
    protected int err_code;

    public MemoAPIException() {

    }

    public MemoAPIException(String messsage, int err_code) {
        super(messsage);
        this.err_code = err_code;
    }

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }
}
