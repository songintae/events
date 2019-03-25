package events.event.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import events.event.domain.Event;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SummaryEventResponse {
    @JsonIgnore
    private Long id;
    private String name;
    private String location;
    private Integer price;
    private Integer attendancesCount;
    private Integer availAbleParticipant;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime beginEnrollmentDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endEnrollmentDateTime;

    public static SummaryEventResponse of(Event event) {
        SummaryEventResponse instance = new SummaryEventResponse();
        instance.id = event.getId();
        instance.name = event.getName();
        instance.location = event.getLocation();
        instance.price = event.getPrice();
        instance.attendancesCount = event.getAttendances().size();
        instance.availAbleParticipant = event.getAvailAbleParticipant();
        instance.beginEnrollmentDateTime = event.getBeginEnrollmentDateTime();
        instance.endEnrollmentDateTime = event.getEndEnrollmentDateTime();

        return instance;
    }

}
