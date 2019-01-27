package events.common;

import events.account.domain.Account;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {
    public static final String USER_SESSION_KEY = "loginUser";

    public static void setUserSession(HttpSession session, Account account) {
        session.setAttribute(USER_SESSION_KEY, account);
    }

    public static Account getUserSession(HttpSession session) {
        return (Account) session.getAttribute(USER_SESSION_KEY);
    }

    public static Account getUserSession(NativeWebRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request.getNativeRequest();
        return getUserSession(httpServletRequest.getSession());
    }
}
