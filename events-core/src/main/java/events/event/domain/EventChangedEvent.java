package events.event.domain;

import lombok.Getter;

@Getter
public class EventChangedEvent {
    private Event source;

    public EventChangedEvent(Event source) {
        this.source = source;
    }
}
