package events.event.dto;

import events.event.domain.Event;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
public class EventRequest {
    @NotNull
    private String name;
    @NotNull
    private String contents;
    @NotNull
    private String location;
    @Min(Event.MIN_PRICE)
    @Max(Event.MAX_PRICE)
    private Integer price;
    @NotNull
    private LocalDateTime beginEnrollmentDateTime;
    @NotNull
    private LocalDateTime endEnrollmentDateTime;
    @NotNull
    private LocalDateTime beginEventDateTime;
    @NotNull
    private LocalDateTime endEventDateTime;
    @Min(Event.MIN_PARTICIPANT)
    @Max(Event.MAX_PARTICIPANT)
    private Integer availAbleParticipant;
}
