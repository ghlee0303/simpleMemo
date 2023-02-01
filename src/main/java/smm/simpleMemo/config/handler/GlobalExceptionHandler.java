package smm.simpleMemo.config.handler;

import org.springframework.web.bind.annotation.*;
import smm.simpleMemo.exception.ValidateException;
import smm.simpleMemo.response.ErrorResult;
import smm.simpleMemo.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.servlet.NoHandlerFoundException;
import smm.simpleMemo.response.ResponseMemo;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseMemo<ErrorResult> notFoundExceptionHandle(NotFoundException e) {
        ErrorResult result = new ErrorResult(e.getMessage(), e.getErr_code());
        return new ResponseMemo<>(404, result);
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseBody
    public ResponseMemo<ErrorResult> validateExceptionHandle(ValidateException e) {
        ErrorResult result = new ErrorResult(e.getMessage(), e.getErr_code());
        return new ResponseMemo<>(409, result);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404Error(NoHandlerFoundException ex, Model model){
        return "error/404"; //custom error page
    }
}
