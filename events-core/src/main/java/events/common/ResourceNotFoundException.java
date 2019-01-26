package events.common;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    String message;
    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
