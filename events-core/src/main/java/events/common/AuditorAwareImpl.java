package events.common;

import events.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class AuditorAwareImpl implements AuditorAware<Account> {
    private AuditorHolder currentUserHolder;

    @Override
    public Optional<Account> getCurrentAuditor() {
        return currentUserHolder.getAuditor();
    }
}
