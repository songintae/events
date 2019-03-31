package events.common.audit;

import events.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class AuditorContext {
    private Account auditor;

    static AuditorContext ofEmpty() {
        return new AuditorContext(null);
    }
}
