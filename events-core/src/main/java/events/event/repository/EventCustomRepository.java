package events.event.repository;

import events.event.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventCustomRepository {
    Page<Event> findEvents(Pageable pageable);
}
