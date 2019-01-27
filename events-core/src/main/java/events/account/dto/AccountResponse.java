package events.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import events.account.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AccountResponse {
    @JsonIgnore
    private Long id;
    private String email;

    public static AccountResponse of(Account account) {
        AccountResponse instance = new AccountResponse();
        instance.id = account.getId();
        instance.email = account.getEmail();
        return instance;
    }

}
