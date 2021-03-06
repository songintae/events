package events.event.controller;


import events.account.domain.Account;
import events.account.domain.LoginUser;
import events.event.dto.SummaryEventResponse;
import events.event.dto.EventRequest;
import events.event.dto.EventResponse;
import events.event.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {
    private EventService eventService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody @Valid EventRequest eventRequest, @LoginUser Account account) {
        EventResponse event = eventService.createEvent(eventRequest, account);
        ControllerLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(event.getId());
        URI location = selfLinkBuilder.toUri();

        Resource<EventResponse> resource = convertEventResponseToResource(event);
        resource.add(linkTo(EventController.class).slash(event.getId()).withRel("update"));
        resource.add(linkTo(EventController.class).slash(event.getId()).withRel("delete"));
        resource.add(new Link("http://localhost:8080/docs/index.html#resources-events-create").withRel("profile"));
        return ResponseEntity.created(location).body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable Long id, @LoginUser Account account) {
        EventResponse event = eventService.readEvent(id);

        Resource<EventResponse> resource = convertEventResponseToResource(event);
        if(event.isRegister(account)) {
            resource.add(linkTo(EventController.class).slash(event.getId()).withRel("update"));
            resource.add(linkTo(EventController.class).slash(event.getId()).withRel("delete"));
        }
        resource.add(new Link("http://localhost:8080/docs/index.html#resources-events-read").withRel("profile"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity readSummaries(@PageableDefault Pageable pageable, PagedResourcesAssembler<SummaryEventResponse> assembler) {
        Page<SummaryEventResponse> page = eventService.readSummaryEvents(pageable);

        PagedResources<Resource<SummaryEventResponse>> response = assembler.toResource(page, event -> {
            Resource<SummaryEventResponse> resource = new Resource<>(event);
            resource.add(linkTo(EventController.class).slash(event.getId()).withRel(Link.REL_SELF));
            resource.add(new Link("http://localhost:8080/docs/index.html#resources-events-list").withRel("profile"));
            return resource;
        });

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody EventRequest request, @LoginUser Account account) {
        EventResponse event =  eventService.updateEvent(id, account, request);

        Resource<EventResponse> resource = convertEventResponseToResource(event);
        resource.add(new Link("http://localhost:8080/docs/index.html#resources-events-update").withRel("profile"));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id, @LoginUser Account account) {
        EventResponse event = eventService.deleteEvent(id, account);

        Resource<Long> resource = new Resource<>(event.getId());
        resource.add(new Link("http://localhost:8080/docs/index.html#resources-events-delete").withRel("profile"));
        resource.add(linkTo(EventController.class).withRel("events"));
        return ResponseEntity.ok(resource);
    }

    private Resource<EventResponse> convertEventResponseToResource(EventResponse event) {
        Resource<EventResponse> resource = new Resource<>(event);
        resource.add(linkTo(EventController.class).slash(event.getId()).withRel(Link.REL_SELF));
        resource.add(linkTo(EventController.class).withRel("events"));
        return resource;
    }
}
