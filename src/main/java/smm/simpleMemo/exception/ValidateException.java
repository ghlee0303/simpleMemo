package smm.simpleMemo.exception;

public class ValidateException extends MemoAPIException{
    public ValidateException() {
    }

    public ValidateException(String messsage, int err_code) {
        super(messsage, err_code);
    }
}
