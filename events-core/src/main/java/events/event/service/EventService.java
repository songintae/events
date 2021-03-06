package events.event.service;


import events.account.domain.Account;
import events.common.ResourceNotFoundException;
import events.event.domain.Event;
import events.event.domain.EventChangedEvent;
import events.event.dto.SummaryEventResponse;
import events.event.dto.EventRequest;
import events.event.dto.EventResponse;
import events.event.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class EventService {
    private EventRepository eventRepository;
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public EventResponse createEvent(EventRequest request, Account register) {
        Event newEvent = Event.builder()
                .name(request.getName())
                .contents(request.getContents())
                .price(request.getPrice())
                .location(request.getLocation())
                .availAbleParticipant(request.getAvailAbleParticipant())
                .beginEnrollmentDateTime(request.getBeginEnrollmentDateTime())
                .endEnrollmentDateTime(request.getEndEnrollmentDateTime())
                .beginEventDateTime(request.getBeginEventDateTime())
                .endEventDateTime(request.getEndEventDateTime())
                .register(register)
                .build();
        Event savedEvent = eventRepository.save(newEvent);
        return EventResponse.of(savedEvent);
    }

    public EventResponse readEvent(Long id) {
        return EventResponse.of(findById(id));
    }

    public Page<SummaryEventResponse> readSummaryEvents(Pageable pageable) {
        return eventRepository.findEvents(pageable).map(SummaryEventResponse::of);
    }

    @Transactional
    public EventResponse updateEvent(Long id, Account account, EventRequest request) {
        Event savedEvent = findById(id);
        savedEvent.amendEvent(account, request);
        eventPublisher.publishEvent(new EventChangedEvent(savedEvent));

        return EventResponse.of(savedEvent);
    }

    Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 Event 입니다."));
    }

    @Transactional
    public EventResponse deleteEvent(Long id, Account account) {
        Event savedEvent = findById(id);
        savedEvent.delete(account);

        return EventResponse.of(savedEvent);
    }
}
