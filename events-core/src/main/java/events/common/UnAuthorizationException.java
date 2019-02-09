package events.common;

import lombok.Getter;

@Getter
public class UnAuthorizationException extends RuntimeException{
    private String message;

    public UnAuthorizationException(String message) {
        super(message);
        this.message = message;
    }
}
