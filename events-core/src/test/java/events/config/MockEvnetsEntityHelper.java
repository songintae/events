package events.config;

import events.account.domain.Account;
import events.account.repository.AccountRepository;
import events.event.domain.Event;
import events.event.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class MockEvnetsEntityHelper {
    private EventRepository eventRepository;
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    public static final String DEFAULT_PASSWORD = "123456789";

    public Event mockEvent() {
        LocalDateTime beginEnrollmentDateTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime beginEventDateTime = beginEnrollmentDateTime.plusMonths(1);
        Event event = Event.builder()
                .name("SpringBoot 스터디")
                .contents("스프링 부트와 JPA 대한 학습")
                .price(20000)
                .location("장은빌딩 18층 카페")
                .availAbleParticipant(20)
                .beginEnrollmentDateTime(beginEnrollmentDateTime)
                .endEnrollmentDateTime(beginEnrollmentDateTime.plusDays(1))
                .beginEventDateTime(beginEventDateTime)
                .endEventDateTime(beginEventDateTime.plusHours(8))
                .register(mockAccount())
                .build();

        return eventRepository.save(event);
    }

    public Account mockAccount() {
        Account account = new Account("mockAccount@email.com", passwordEncoder.encode(DEFAULT_PASSWORD));
        return accountRepository.save(account);
    }

    public Account mockAccount(String email) {
        Account account = new Account(email, passwordEncoder.encode(DEFAULT_PASSWORD));
        return accountRepository.save(account);
    }
}
