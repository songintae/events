package events.config;

import events.account.domain.Account;
import events.common.AuditorHolder;
import events.common.SessionUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class AuditorInterceptor implements HandlerInterceptor {
    private AuditorHolder auditorHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Account loginUser = SessionUtils.getUserSession(request.getSession());
        auditorHolder.setAuditor(loginUser);
        return true;
    }
}
