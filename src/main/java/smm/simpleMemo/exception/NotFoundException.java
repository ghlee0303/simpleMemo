package smm.simpleMemo.exception;

public class NotFoundException extends MemoAPIException {
    public NotFoundException() {
    }

    public NotFoundException(String messsage, int err_code) {
        super(messsage, err_code);
    }
}
