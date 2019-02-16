package events.event.repository;

import events.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface EventRepository extends JpaRepository<Event, Long>, EventCustomRepository , RevisionRepository<Event, Long, Integer> {
}