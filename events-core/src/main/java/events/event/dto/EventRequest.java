package events.event.dto;

import events.event.domain.Event;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
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

    @Builder
    public EventRequest(@NotNull String name, @NotNull String contents, @NotNull String location, @Min(Event.MIN_PRICE) @Max(Event.MAX_PRICE) Integer price, @NotNull LocalDateTime beginEnrollmentDateTime, @NotNull LocalDateTime endEnrollmentDateTime, @NotNull LocalDateTime beginEventDateTime, @NotNull LocalDateTime endEventDateTime, @Min(Event.MIN_PARTICIPANT) @Max(Event.MAX_PARTICIPANT) Integer availAbleParticipant) {
        this.name = name;
        this.contents = contents;
        this.location = location;
        this.price = price;
        this.beginEnrollmentDateTime = beginEnrollmentDateTime;
        this.endEnrollmentDateTime = endEnrollmentDateTime;
        this.beginEventDateTime = beginEventDateTime;
        this.endEventDateTime = endEventDateTime;
        this.availAbleParticipant = availAbleParticipant;
    }
}
