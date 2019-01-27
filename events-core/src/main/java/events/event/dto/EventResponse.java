package events.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import events.account.domain.Account;
import events.account.dto.AccountResponse;
import events.event.domain.Event;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Getter
public class EventResponse {
    @JsonIgnore
    private Long id;
    private String name;
    private String contents;
    private String location;
    private Integer price;
    private Integer availAbleParticipant;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime beginEnrollmentDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endEnrollmentDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime beginEventDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endEventDateTime;

    private AccountResponse register;


    public static EventResponse of(Event event) {
        EventResponse instance = new EventResponse();
        instance.id = event.getId();
        instance.name = event.getName();
        instance.contents = event.getContents();
        instance.location = event.getLocation();
        instance.price = event.getPrice();
        instance.availAbleParticipant = event.getAvailAbleParticipant();
        instance.beginEnrollmentDateTime = event.getBeginEnrollmentDateTime();
        instance.endEnrollmentDateTime = event.getEndEnrollmentDateTime();
        instance.beginEventDateTime = event.getBeginEventDateTime();
        instance.endEventDateTime = event.getEndEventDateTime();
        instance.register = AccountResponse.of(event.getRegister());

        return instance;
    }

    public boolean isRegister(Account account) {
        if(ObjectUtils.isEmpty(account)) {
            return false;
        }
        
        return register.getId().equals(account.getId());
    }
}
