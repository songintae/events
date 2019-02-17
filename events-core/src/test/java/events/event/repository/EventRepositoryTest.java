package events.event.repository;

import events.account.domain.Account;
import events.account.repository.AccountRepository;
import events.config.EventsJpaTestConfiguration;
import events.event.domain.Event;
import events.event.dto.BriefEventResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ContextConfiguration(classes = EventsJpaTestConfiguration.class)
@DataJpaTest
class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("이벤트 목록 조회 Query 테스트")
    void findEvents() {
        //given
        saveEvent();
        Pageable pageable = PageRequest.of(0, 10);

        //when
            Page<BriefEventResponse> events = eventRepository.findEvents(pageable);

        //then
        assertThat(events.getTotalPages()).isEqualTo(1);
        assertThat(events.getNumberOfElements()).isEqualTo(1);

        BriefEventResponse event = events.getContent().get(0);
        assertThat(event.getPrice()).isEqualTo(20000);
        assertThat(event.getAttendancesCount()).isEqualTo(0);
    }

    private Event saveEvent() {
        LocalDateTime beginEnrollmentDateTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime beginEventDateTime = beginEnrollmentDateTime.plusMonths(1);
        Account register = accountRepository.save(new Account("test@email", "123456"));

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
                .register(register)
                .build();

        return eventRepository.save(event);
    }

}