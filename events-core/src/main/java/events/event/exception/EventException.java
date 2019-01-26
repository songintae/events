package events.event.exception;

import lombok.Getter;

@Getter
public class EventException extends RuntimeException{
    private String message;

    public EventException(String message) {
        super(message);
        this.message = message;
    }
}
