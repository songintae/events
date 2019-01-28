package events.account;

import events.account.domain.Account;
import events.account.service.AccountService;
import events.common.SessionUtils;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class LoginController {
    private AccountService accountService;

    @PostMapping("login")
    public void login(HttpSession session, @RequestBody String email, @RequestBody String password) {
        Account principal = accountService.certificate(email, password);
        SessionUtils.setUserSession(session, principal);
    }
}
