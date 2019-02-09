package events.common;

import events.account.exception.AccountException;
import events.event.exception.EventException;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@AllArgsConstructor
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    private MessageSourceAccessor messageSourceAccessor;

    @ExceptionHandler(value = {EventException.class, AccountException.class})
    public ResponseEntity badRequestExceptionHandler(RuntimeException exception) {
        ErrorMessage errorMessage = ErrorMessage.of(exception.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity notFoundExceptionHandler(ResourceNotFoundException exception) {
        ErrorMessage errorMessage = ErrorMessage.of(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(value = UnAuthorizationException.class)
    public ResponseEntity unAuthorizationHandler(UnAuthorizationException exception) {
        ErrorMessage errorMessage = ErrorMessage.of(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity validExceptionHandler(MethodArgumentNotValidException exception) {
        ErrorMessage errorMessage = exception.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(this::getErrorMessage)
                .orElse(ErrorMessage.ofEmpty());
        return ResponseEntity.badRequest().body(errorMessage);
    }

    private ErrorMessage getErrorMessage(ObjectError objectError) {
        FieldError fieldError = (FieldError) objectError;
        Optional<String> code = getCode(fieldError);
        if (!code.isPresent()) {
            return ErrorMessage.ofEmpty();
        }
        String message = messageSourceAccessor.getMessage(code.get(), fieldError.getArguments(), fieldError.getDefaultMessage());
        return ErrorMessage.of(message);
    }

    private Optional<String> getCode(FieldError fieldError) {
        String[] codes = fieldError.getCodes();
        if (codes.length == 0) {
            return Optional.empty();
        }
        return Optional.of(codes[0]);
    }
}
