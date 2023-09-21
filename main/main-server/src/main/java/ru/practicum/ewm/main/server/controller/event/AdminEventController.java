package ru.practicum.ewm.main.server.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.event.EventFullDto;
import ru.practicum.ewm.main.api.event.EventStatus;
import ru.practicum.ewm.main.api.event.EventUpdateAdminRequest;
import ru.practicum.ewm.main.server.model.EventGetAllByAdminParameters;
import ru.practicum.ewm.main.server.service.EventService;
import ru.practicum.ewm.util.aspect.log.ToLog;
import ru.practicum.ewm.util.validator.StartBeforeEndDateConstraint;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.util.constant.Constants.DATE_TIME_FORMAT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
@Validated
@ToLog
public class AdminEventController {
    private final EventService service;

    @GetMapping
    @StartBeforeEndDateConstraint
    public Collection<EventFullDto> getAllByAdmin(
            @RequestParam(required = false, name = "rangeStart")
            @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime start,
            @RequestParam(required = false, name = "rangeEnd")
            @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime end,
            @RequestParam(required = false) Boolean onlyPending,
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) List<EventStatus> states,
            @RequestParam(defaultValue = "0") @Min(0) int from,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        return service.getAllByAdmin(EventGetAllByAdminParameters.builder()
                .start(start)
                .end(end)
                .onlyPending(onlyPending)
                .users(users)
                .categories(categories)
                .states(states)
                .from(from)
                .size(size)
                .build());
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateByAdmin(@RequestBody @Valid EventUpdateAdminRequest eventUpdateAdminRequest,
                                      @PathVariable @Min(1) Long eventId) {
        return service.updateByAdmin(eventUpdateAdminRequest, eventId);
    }
}
