package dorun.project.routineapp.common.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;
    private List<ErrorField> errors;

    public BusinessException(String message, ErrorField error) {
        super(message);
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errors = new ArrayList<>();
    }

    public BusinessException(List<ErrorField> errors) {
        this.errors = errors;
    }

    public BusinessException(ErrorCode errorCode, List<ErrorField> errors) {
        this.errorCode = errorCode;
        this.errors = errors;
    }

}
