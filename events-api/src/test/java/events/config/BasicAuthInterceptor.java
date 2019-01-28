package events.config;

import events.account.domain.Account;
import events.account.service.AccountService;
import events.common.SessionUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
@AllArgsConstructor
public class BasicAuthInterceptor implements HandlerInterceptor {
    private AccountService accountService;

    public static final String BASIC_AUTH_HEADER = "Basic ";
    public static final String BASIC_AUTH_SPLITTER = ":";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith(BASIC_AUTH_HEADER)) {
           return true;
        }
        String encodedCredentials = authorization.replaceAll(BASIC_AUTH_HEADER, "");
        String credentials = new String(Base64.getDecoder().decode(encodedCredentials), StandardCharsets.UTF_8);
        String[] values = credentials.split(BASIC_AUTH_SPLITTER);
        Account account = accountService.certificate(values[0], values[1]);

        HttpSession session = request.getSession();
        SessionUtils.setUserSession(session, account);

        return true;
    }
}
