package events.config;

import events.account.domain.Account;
import events.common.SessionUtils;
import events.common.audit.AuditorContext;
import events.common.audit.AuditorContextHolder;
import lombok.AllArgsConstructor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@AllArgsConstructor
public class AuditorFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Account loginUser = SessionUtils.getUserSession(httpServletRequest.getSession());
        AuditorContextHolder.setContext(new AuditorContext(loginUser));
        chain.doFilter(request, response);
    }
}
