package events.common;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class GlobalAdvice {

    @InitBinder
    public void initWebDataBinder(WebDataBinder webDataBinder) {
        webDataBinder.initDirectFieldAccess();
    }
}
