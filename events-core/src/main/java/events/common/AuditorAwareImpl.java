package events.common;

import events.account.domain.Account;
import events.account.domain.AccountDetail;
import events.account.repository.AccountRepository;
import events.common.audit.AuditorContextHolder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Account> {
    @Override
    public Optional<Account> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(AccountDetail.class::cast)
                .map(AccountDetail::getAccount);
    }
}
