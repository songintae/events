package events.event.domain;


import events.account.domain.Account;
import events.common.UnAuthorizationException;
import events.event.exception.EventException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("Event Entity 테스트")
class EventTest {

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event();
    }

    @Test
    @DisplayName("Event명은 필수 입력값 테스트")
    void amendName() {
        //when & then
        event.amendName("Spring Boot Study");

        //when & then
        assertThrows(EventException.class, () -> {
            event.amendName("");});
        assertThrows(EventException.class, () -> {
            event.amendName(null);});
    }


    @Test
    @DisplayName("Event명은 필수 입력값 테스트")
    void amendContents() {
        //when & then
        event.amendContents("Spring Boot로 만드는 REST API");

        //when & then
        assertThrows(EventException.class, () -> {event.amendContents("");});
        assertThrows(EventException.class, () -> {event.amendContents(null);});
    }


    @Test
    @DisplayName("Event는 0~20만원 사이의 가격만 설정할 수 있다.")
    void amendPrice() {
        //when & then
        event.amendPrice(Event.MIN_PRICE);
        assertThat(event.getPrice()).isEqualTo(Event.MIN_PRICE);

        //when & then
        event.amendPrice(Event.MAX_PRICE);
        assertThat(event.getPrice()).isEqualTo(Event.MAX_PRICE);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Event 잘못된 금액 입력 테스트")
    void amendPriceFail(int price) {
        //when & then
        assertThrows(EventException.class, () -> event.amendPrice(price));
    }

    static Object[] amendPriceFail() {
        return new Object[]{
                new Object[]{Event.MIN_PRICE - 1},
                new Object[]{Event.MAX_PRICE + 1}
        };
    }

    @Test
    @DisplayName("한명이라도 등록자가 있다면 가격은 수정할 수 없다.")
    void amendPriceFailByRegister() {
        //given
        event.amendAvailAbleParticipant(1);
        event.amendPrice(100000);
        Attendance attendance = new Attendance();
        event.addAttendance(attendance);

        //when & then
        assertThrows(EventException.class, () -> event.amendPrice(1000));
    }


    @Test
    @DisplayName("Event 장소는 필수값입니다.")
    void amendLocation() {
        //when & then
        event.amendLocation("장은빌딩");
        assertThat(event.getLocation()).isEqualTo("장은빌딩");

        //when & then
        assertThrows(EventException.class, () -> event.amendLocation(""));
    }


    @Test
    @DisplayName("참석자 인원은 0~100명만 설정할 수 있다.")
    void amendNumberOfParticipant() {
        //when & then
        event.amendAvailAbleParticipant(Event.MIN_PARTICIPANT);
        assertThat(event.getAvailAbleParticipant()).isEqualTo(Event.MIN_PARTICIPANT);

        event.amendAvailAbleParticipant(Event.MAX_PARTICIPANT);
        assertThat(event.getAvailAbleParticipant()).isEqualTo(Event.MAX_PARTICIPANT);

        //when & then
        assertThrows(EventException.class, () -> event.amendAvailAbleParticipant(Event.MIN_PARTICIPANT - 1));
        assertThrows(EventException.class, () -> event.amendAvailAbleParticipant(Event.MAX_PARTICIPANT + 1));
    }

    @Test
    @DisplayName("참석자 인원은 현재 수강 인원보다 작게 수정할 수 없다.")
    void amendNumberOfParticipantFail() {
        event.amendAvailAbleParticipant(2);
        Attendance attendance1 = new Attendance();
        Attendance attendance2 = new Attendance();
        attendance1.setId(1L);
        attendance2.setId(2L);
        event.addAttendance(attendance1);
        event.addAttendance(attendance2);


        // when & then
        assertThrows(EventException.class, () -> event.amendAvailAbleParticipant(1));

    }


    @Test
    @DisplayName("이벤트 등록 시작시간, 등록 마감시간, 이벤트 시작시간, 이벤트 종료시간 테스트")
    void timeTest() {
        LocalDateTime past = LocalDateTime.now().minusMinutes(1);
        LocalDateTime before = LocalDateTime.now();
        LocalDateTime after = LocalDateTime.now().plusMinutes(1);


        // when & then
        assertThrows(EventException.class, () -> event.amendBeginEnrollmentDateTime(past));
        assertThrows(EventException.class, () -> event.amendEndEnrollmentDateTime(past));
        assertThrows(EventException.class, () -> event.amendBeginEventDateTime(past));
        assertThrows(EventException.class, () -> event.amendEndEventDateTime(past));

        // when & then
        event.amendBeginEnrollmentDateTime(after);
        assertThrows(EventException.class, () -> event.amendEndEnrollmentDateTime(before));

        // when & then
        event.amendEndEnrollmentDateTime(after);
        event.amendBeginEventDateTime(after);
        assertThrows(EventException.class, () -> event.amendEndEventDateTime(before));
    }


    @Test
    @DisplayName("이벤트 시작일은 이벤트 등록 마감일 이후이거나, 이벤트 등록시작일로부터 1달안에 시작해야합니다.")
    void beginEventDate() {
        event.amendBeginEnrollmentDateTime(LocalDateTime.now().plusMinutes(1));
        event.amendEndEnrollmentDateTime(LocalDateTime.now().plusMinutes(20));

        //when & then
        assertThrows(EventException.class, () -> event.amendBeginEventDateTime(event.getBeginEnrollmentDateTime().plusMonths(1).plusDays(1)));
    }

    @Test
    @DisplayName("Event가 수정 가능한 기간인지 판단하는 로직 테스트")
    void isBeforeOfAmendDeadLine() {
        event.amendBeginEventDateTime(LocalDateTime.now().plusDays(8));

        assertThat(event.isBeforeOfAmendDeadLine()).isTrue();
    }

    @Test
    @DisplayName("Event에 참가자는 참석가능 인원까지만 참가할 수 있다.")
    void addAttendance() {
        event.amendAvailAbleParticipant(1);
        Attendance attendance = new Attendance();
        attendance.setId(1L);
        event.addAttendance(attendance);

        //when & then
        assertThrows(EventException.class, () -> event.addAttendance(new Attendance()));
    }

    @Test
    @DisplayName("Event 삭제 테스트")
    void delete() {
        // given
        Account account = new Account();
        account.setId(1L);

        event.amendRegister(account);
        event.amendAvailAbleParticipant(1);
        event.addAttendance(new Attendance());

        // when & then
        assertThrows(UnAuthorizationException.class, () -> event.delete(null));

        assertThrows(EventException.class, () -> event.delete(account));
    }

    @Test
    @DisplayName("Event 등록자가 맞는지 확인하는 테스트")
    void isRegister() {
        Account account = new Account();
        account.setId(1L);

        event.amendRegister(account);

        // when & then
        assertThat(event.isRegister(account)).isTrue();
        assertThat(event.isRegister(new Account())).isFalse();
    }
}