package events.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import events.event.domain.Event;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
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

    public static EventResponse of(Event event) {
        EventResponse instance = new EventResponse();
        instance.setId(event.getId());
        instance.setName(event.getName());
        instance.setContents(event.getContents());
        instance.setLocation(event.getLocation());
        instance.setPrice(event.getPrice());
        instance.setAvailAbleParticipant(event.getAvailAbleParticipant());
        instance.setBeginEnrollmentDateTime(event.getBeginEnrollmentDateTime());
        instance.setEndEnrollmentDateTime(event.getEndEnrollmentDateTime());
        instance.setBeginEventDateTime(event.getBeginEventDateTime());
        instance.setEndEventDateTime(event.getEndEventDateTime());

        return instance;
    }
}
