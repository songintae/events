package events.event.listener;

import events.event.domain.Attendance;
import events.event.domain.Event;
import events.event.domain.EventChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Objects;
import java.util.Set;

@Component
public class EventListener {

    @TransactionalEventListener
    public void handleChangedEvent(EventChangedEvent eventChangedEvent) {
        if (Objects.isNull(eventChangedEvent.getSource())) {
            return;
        }

        Event source = eventChangedEvent.getSource();
        Set<Attendance> attendances = source.getAttendances();
        attendances.forEach(attendance -> sendAlarm(source, attendance));
    }

    //TODO : 사용자 알림 보내는건 나중에 스터디 회의 후 어떤 방식으로 할지 구현.
    private void sendAlarm(Event event, Attendance attendance) {
        System.out.println(attendance.toString());
    }
}
