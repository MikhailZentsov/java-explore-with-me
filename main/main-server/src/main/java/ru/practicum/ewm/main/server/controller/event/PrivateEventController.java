package ru.practicum.ewm.main.server.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.event.EventFullDto;
import ru.practicum.ewm.main.api.event.EventShortDto;
import ru.practicum.ewm.main.api.event.EventUpdateUserRequest;
import ru.practicum.ewm.main.api.event.NewEventDto;
import ru.practicum.ewm.main.api.event.RequestStatusUpdateRequest;
import ru.practicum.ewm.main.api.event.RequestStatusUpdateResult;
import ru.practicum.ewm.main.api.request.ParticipationRequestDto;
import ru.practicum.ewm.main.server.service.EventService;
import ru.practicum.ewm.util.aspect.log.ToLog;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Validated
@ToLog
public class PrivateEventController {
    private final EventService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@RequestBody @Valid NewEventDto newEventDto, @PathVariable @Min(1) long userId) {
        return service.create(newEventDto, userId);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getByIdByUser(@PathVariable @Min(1) long eventId, @PathVariable @Min(1) long userId) {
        return service.getByIdByUser(eventId, userId);
    }

    @GetMapping
    public Collection<EventShortDto> getAllByUser(@PathVariable @Min(1) long userId,
                                                  @RequestParam(defaultValue = "0") @Min(0) int from,
                                                  @RequestParam(defaultValue = "10") @Min(1) int size) {
        return service.getAllByUser(userId, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateByUser(@RequestBody @Valid EventUpdateUserRequest eventUpdateUserRequest,
                                     @PathVariable @Min(1) long eventId, @PathVariable @Min(1) long userId) {
        return service.updateByUser(eventUpdateUserRequest, eventId, userId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable @Min(1) Long userId,
                                                     @PathVariable @Min(1) Long eventId) {
        return service.getRequests(eventId, userId);
    }

    @PatchMapping("/{eventId}/requests")
    public RequestStatusUpdateResult updateRequestStatus(@RequestBody @Valid RequestStatusUpdateRequest dto,
                                                         @PathVariable @Min(1) Long userId,
                                                         @PathVariable @Min(1) Long eventId) {
        return service.updateRequestStatus(dto, eventId, userId);
    }
}
