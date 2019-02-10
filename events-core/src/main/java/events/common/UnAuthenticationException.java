package events.common;

import lombok.Getter;

@Getter
public class UnAuthenticationException extends RuntimeException {
    private String message;

    public UnAuthenticationException(String message) {
        super(message);
        this.message = message;
    }
}
