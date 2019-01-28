package events.common;

import events.account.domain.Account;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class AuditorHolder {
    private Optional<Account> auditor = Optional.empty();

    public void setAuditor(Account auditor) {
        this.auditor = Optional.ofNullable(auditor);
    }
}
