package events.event.service;


import events.account.domain.Account;
import events.common.ResourceNotFoundException;
import events.event.domain.Event;
import events.event.dto.BriefEventResponse;
import events.event.dto.EventRequest;
import events.event.dto.EventResponse;
import events.event.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class EventService {
    private EventRepository eventRepository;

    @Transactional
    public EventResponse createEvent(EventRequest request, Account register) {
        Event newEvent = Event.of(request, register);
        Event savedEvent = eventRepository.save(newEvent);

        return EventResponse.of(savedEvent);
    }

    public EventResponse readEvent(Long id) {
        return EventResponse.of(findById(id));
    }

    public Page<BriefEventResponse> readEvents(Pageable pageable) {
        return eventRepository.findEvents(pageable);
    }

    @Transactional
    public EventResponse updateEvent(Long id, EventRequest request) {
        Event savedEvent = findById(id);
        savedEvent.amendEvent(request);

        return EventResponse.of(savedEvent);
    }

    Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 Event 입니다."));
    }
    @Transactional
    public EventResponse deleteEvent(Long id) {
        Event savedEvent = findById(id);
        savedEvent.delete();
        return EventResponse.of(savedEvent);
    }
}
