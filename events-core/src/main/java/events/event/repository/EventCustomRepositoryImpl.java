package events.event.repository;

import com.querydsl.jpa.JPQLQuery;
import events.event.domain.Event;
import events.event.domain.QAttendance;
import events.event.domain.QEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;

public class EventCustomRepositoryImpl extends QuerydslRepositorySupport implements EventCustomRepository{
    private final QEvent event = QEvent.event;
    private final QAttendance attendance = QAttendance.attendance;

    public EventCustomRepositoryImpl() {
        super(Event.class);
    }

    @Override
    public Page<Event> findEvents(Pageable pageable) {
        JPQLQuery<Event> query = from(event)
                .leftJoin(event.attendances, attendance).fetchJoin()
                .where(event.attendances.size().lt(event.availAbleParticipant)
                        .and(event.endEnrollmentDateTime.after(LocalDateTime.now())))
                .orderBy(event.id.desc());
        JPQLQuery<Event> pageQuery = getQuerydsl().applyPagination(pageable, query);

        return new PageImpl<>(pageQuery.fetch(), pageable, pageQuery.fetchResults().getTotal());
    }
}
