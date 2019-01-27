package events.account.service;

import events.account.domain.Account;
import events.account.exception.AccountException;
import events.account.repository.AccountRepository;
import events.common.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountService {
    private PasswordEncoder passwordEncoder;
    private AccountRepository accountRepository;

    public Account createAccount(String email, String password) {
        Account account = new Account(email, passwordEncoder.encode(password));
        return accountRepository.save(account);
    }

    public Account login(String email, String password) {
        Account savedAccount = findByEmail(email);
        if(!savedAccount.matchPassword(password, passwordEncoder))
             throw new AccountException("계정 비밀번호가 올바르지 않습니다.");

        return savedAccount;
    }


    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("계정을 찾을 수 없습니다."));
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("계정을 찾을 수 없습니다."));
    }
}
