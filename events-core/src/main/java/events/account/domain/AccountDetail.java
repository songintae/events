package events.account.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class AccountDetail extends User {
    private Account account;

    public AccountDetail(Account account) {
        super(account.getEmail(), account.getPassword(), authorities(account));
        this.account = account;
    }

    private static List<GrantedAuthority> authorities(Account account) {
        return AuthorityUtils.createAuthorityList();
    }

}
