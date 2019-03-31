package events.common;

import events.account.domain.Account;
import events.account.repository.AccountRepository;
import events.common.audit.AuditorContextHolder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class AuditorAwareImpl implements AuditorAware<Account> {
    private AccountRepository accountRepository;
    @Override
    public Optional<Account> getCurrentAuditor() {
        Account auditor = AuditorContextHolder.getContext().getAuditor();
        if(auditor == null) {
            return Optional.empty();
        }
        return accountRepository.findByEmail(auditor.getEmail());
    }
}
