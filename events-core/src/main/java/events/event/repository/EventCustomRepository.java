package events.event.repository;

import events.event.dto.BriefEventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventCustomRepository {
    Page<BriefEventResponse> findEvents(Pageable pageable);
}
