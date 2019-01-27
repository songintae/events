package events.event.service;

import events.account.domain.Account;
import events.config.EventsTestConfiguration;
import events.config.MockEvnetsEntityHelper;
import events.event.domain.Event;
import events.event.dto.EventRequest;
import events.event.dto.EventResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventsTestConfiguration.class)
class EventServiceTest {
    @Autowired
    private EventService eventService;
    @Autowired
    private MockEvnetsEntityHelper mockEvnetsEntityHelper;

    @Test
    @DisplayName("이벤트 생성 테스트")
    void createEventTest() {
        //given
        Account register = mockEvnetsEntityHelper.mockAccount();

        EventRequest request = new EventRequest();
        request.setName("SpringBoot 스터디");
        request.setContents("스프링 부트와 JPA 대한 학습");
        request.setPrice(10000);
        request.setLocation("장은빌딩 18층 카페");
        request.setAvailAbleParticipant(20);
        LocalDateTime beginEnrollmentDateTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime beginEventDateTime = beginEnrollmentDateTime.plusMonths(1);
        request.setBeginEnrollmentDateTime(beginEnrollmentDateTime);
        request.setEndEnrollmentDateTime(beginEnrollmentDateTime.plusDays(1));
        request.setBeginEventDateTime(beginEventDateTime);
        request.setEndEventDateTime(beginEventDateTime.plusHours(8));

        //when
        EventResponse event = eventService.createEvent(request, register);

        //then
        Event savedEvent = eventService.findById(event.getId());
        assertThat(savedEvent.getName()).isEqualTo(request.getName());
        assertThat(savedEvent.getContents()).isEqualTo(request.getContents());
        assertThat(savedEvent.getRegister()).isEqualTo(register);
    }


}