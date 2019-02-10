package events.config;

import events.account.domain.Account;
import events.common.SessionUtils;
import events.common.UnAuthenticationException;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!HttpMethod.GET.matches(request.getMethod())) {
            Optional<Account> account = Optional.ofNullable(SessionUtils.getUserSession(request.getSession()));
            account.orElseThrow(() -> new UnAuthenticationException("해당 요청은 인증된 사용자만 이용할 수 있습니다."));
        }

        return true;
    }


}
