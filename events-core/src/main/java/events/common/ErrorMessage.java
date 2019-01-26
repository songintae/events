package events.common;

import lombok.Getter;

@Getter
public class ErrorMessage {
    String errorMessage;

    public static ErrorMessage of(String message) {
        ErrorMessage instance = new ErrorMessage();
        instance.errorMessage = message;
        return instance;
    }

    public static ErrorMessage ofEmpty() {
        return of("시스템에 문제가 발생했습니다. 잠시후 요청바랍니다.");
    }
}
