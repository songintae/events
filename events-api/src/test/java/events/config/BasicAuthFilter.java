package events.config;


import events.account.domain.Account;
import events.account.service.AccountService;
import events.common.SessionUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@AllArgsConstructor
public class BasicAuthFilter implements Filter {
    private AccountService accountService;

    public static final String BASIC_AUTH_HEADER = "Basic ";
    public static final String BASIC_AUTH_SPLITTER = ":";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String authorization = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith(BASIC_AUTH_HEADER)) {
            chain.doFilter(request, response);
            return;
        }
        String encodedCredentials = authorization.replaceAll(BASIC_AUTH_HEADER, "");
        String credentials = new String(Base64.getDecoder().decode(encodedCredentials), StandardCharsets.UTF_8);
        String[] values = credentials.split(BASIC_AUTH_SPLITTER);
        Account account = accountService.certificate(values[0], values[1]);

        HttpSession session = httpRequest.getSession();
        SessionUtils.setUserSession(session, account);

        chain.doFilter(request, response);
    }
}
