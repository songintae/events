package events.event.domain;

import events.account.domain.Account;
import events.common.BaseEntity;
import events.common.UnAuthorizationException;
import events.event.dto.EventRequest;
import events.event.exception.EventException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Audited
@Entity
public class Event extends BaseEntity {
    public final static int MIN_PRICE = 0;
    public final static int MAX_PRICE = 200000;
    public final static int MIN_PARTICIPANT = 0;
    public final static int MAX_PARTICIPANT = 100;

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String contents;
    private String location;
    private Integer price;
    private Integer availAbleParticipant;
    private LocalDateTime beginEnrollmentDateTime = LocalDateTime.now();
    private LocalDateTime endEnrollmentDateTime = LocalDateTime.now();
    private LocalDateTime beginEventDateTime = LocalDateTime.now();
    private LocalDateTime endEventDateTime = LocalDateTime.now();

    @NotAudited
    @OneToMany(mappedBy = "event")
    private Set<Attendance> attendances = new HashSet<>();

    @OneToOne
    private Account register;


    boolean isBeforeOfAmendDeadLine() {
        LocalDateTime deadLine = beginEventDateTime.minusDays(7);
        return !LocalDateTime.now().isAfter(deadLine);
    }

    public void delete(Account account) {
        if (!isRegister(account)) {
            throw new UnAuthorizationException("이벤트 등록자만 수정할 수 있습니다.");
        }

        if (!attendances.isEmpty()) {
            throw new EventException("수강인원이 1명이라도 존재하면 이벤트를 삭제할 수 없습니다.");
        }
        super.delete();
    }

    boolean isEnableAttend() {
        return attendances.size() < availAbleParticipant;
    }

    void amendName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new EventException("이벤트 명은 필수 입력값 입니다.");
        }
        this.name = name;
    }

    void amendContents(String contents) {
        if (StringUtils.isEmpty(contents)) {
            throw new EventException("이벤트 설명은 필수 입력값 입니다.");
        }
        this.contents = contents;
    }

    void amendPrice(Integer price) {
        if (!attendances.isEmpty()) {
            throw new EventException("참석인원이 하명이라도 있으면 가격을 수정할 수 없습니다.");
        }

        if (price == null || price < MIN_PRICE || price > MAX_PRICE) {
            throw new EventException("이벤트 가격은 0~20만원 사이로만 가능합니다.");
        }
        this.price = price;
    }

    void amendLocation(String location) {
        if (StringUtils.isEmpty(location)) {
            throw new EventException("이벤트 진행 장소는 필수로 입력해야합니다.");
        }
        this.location = location;
    }

    void amendAvailAbleParticipant(Integer availAbleParticipant) {
        if (availAbleParticipant == null || availAbleParticipant < MIN_PARTICIPANT || availAbleParticipant > MAX_PARTICIPANT) {
            throw new EventException("이벤트 참석자 가능인원은 0~100명 사이로만 설정해야합니다.");
        }

        if (this.attendances.size() > availAbleParticipant) {
            throw new EventException("참석인원보다 참석가능인원을 작게 설정할 수 없습니다.");
        }
        this.availAbleParticipant = availAbleParticipant;
    }


    void amendBeginEnrollmentDateTime(LocalDateTime beginEnrollmentDateTime) {
        if (beginEnrollmentDateTime.isBefore(LocalDateTime.now())) {
            throw new EventException("등록 시작시간을 과거시간으로 등록할 수 없습니다.");
        }
        this.beginEnrollmentDateTime = beginEnrollmentDateTime;
    }

    void amendEndEnrollmentDateTime(LocalDateTime endEnrollmentDateTime) {
        if (endEnrollmentDateTime.isBefore(LocalDateTime.now())) {
            throw new EventException("등록 마감시간을 과거시간으로 등록할 수 없습니다.");
        }

        if (endEnrollmentDateTime.isBefore(beginEnrollmentDateTime)) {
            throw new EventException("등록 마감시간을 등록시작시간보다 과거시간으로 등록할 수 없습니다.");
        }
        this.endEnrollmentDateTime = endEnrollmentDateTime;
    }

    void amendBeginEventDateTime(LocalDateTime beginEventDateTime) {
        if (beginEventDateTime.isBefore(LocalDateTime.now())) {
            throw new EventException("이벤트 시작시간을 과거시간으로 등록할 수 없습니다.");
        }

        if (beginEventDateTime.isBefore(endEnrollmentDateTime)) {
            throw new EventException("이벤트 시작시간을 이벤트 등록 마감시간 이전으로 등록할 수 없습니다.");
        }

        LocalDateTime maxBeginEventDateTime = beginEnrollmentDateTime.plusMonths(1);
        if (beginEventDateTime.isAfter(maxBeginEventDateTime)) {
            throw new EventException("이벤트 시작시간을 이벤트 등록 시작시간 한달 이내로 설정 해야합니다.");
        }
        this.beginEventDateTime = beginEventDateTime;
    }

    void amendEndEventDateTime(LocalDateTime endEventDateTime) {
        if (endEventDateTime.isBefore(LocalDateTime.now())) {
            throw new EventException("이벤트 종료시간을 과거시간으로 등록할 수 없습니다.");
        }

        if (endEventDateTime.isBefore(beginEventDateTime)) {
            throw new EventException("이벤트 종료시간을 에벤트 시작시간보다 과거시간으로 등록할 수 없습니다.");
        }

        this.endEventDateTime = endEventDateTime;
    }

    void addAttendance(Attendance attendance) {
        if (!isEnableAttend()) {
            throw new EventException("이벤트 등록 인원을 초과했습니다.");
        }
        attendance.setEvent(this);
        attendances.add(attendance);
    }

    void amendRegister(Account register) {
        if (ObjectUtils.isEmpty(register)) {
            throw new EventException("이벤트 등록자는 필수 항목입니다.");
        }
        this.register = register;
    }

    public static Event of(EventRequest request, Account register) {
        Event instance = new Event();
        instance.map(request);
        instance.amendRegister(register);

        return instance;
    }

    public void amendEvent(Account account, EventRequest request) {
        if (!isRegister(account)) {
            throw new UnAuthorizationException("이벤트 등록자만 수정할 수 있습니다.");
        }

        if (!this.isBeforeOfAmendDeadLine()) {
            throw new EventException("이벤트는 시작 시간보다 최소 1주일 전에만 수정 가능합니다.");
        }
        this.map(request);
    }

    private void map(EventRequest request) {
        this.amendName(request.getName());
        this.amendContents(request.getContents());
        this.amendLocation(request.getLocation());
        this.amendPrice(request.getPrice());
        this.amendBeginEnrollmentDateTime(request.getBeginEnrollmentDateTime());
        this.amendEndEnrollmentDateTime(request.getEndEnrollmentDateTime());
        this.amendBeginEventDateTime(request.getBeginEventDateTime());
        this.amendEndEventDateTime(request.getEndEventDateTime());
        this.amendAvailAbleParticipant(request.getAvailAbleParticipant());
    }

    boolean isRegister(Account account) {
        return register.equals(account);
    }
}
