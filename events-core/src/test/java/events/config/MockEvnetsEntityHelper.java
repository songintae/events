package events.config;

import events.account.domain.Account;
import events.account.repository.AccountRepository;
import events.event.domain.Event;
import events.event.dto.EventRequest;
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
        EventRequest request = new EventRequest();
        request.setName("SpringBoot 스터디");
        request.setContents("스프링 부트와 JPA 대한 학습");
        request.setPrice(20000);
        request.setLocation("장은빌딩 18층 카페");
        request.setAvailAbleParticipant(20);
        LocalDateTime beginEnrollmentDateTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime beginEventDateTime = beginEnrollmentDateTime.plusMonths(1);
        request.setBeginEnrollmentDateTime(beginEnrollmentDateTime);
        request.setEndEnrollmentDateTime(beginEnrollmentDateTime.plusDays(1));
        request.setBeginEventDateTime(beginEventDateTime);
        request.setEndEventDateTime(beginEventDateTime.plusHours(8));
        return eventRepository.save(Event.of(request, mockAccount()));
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
