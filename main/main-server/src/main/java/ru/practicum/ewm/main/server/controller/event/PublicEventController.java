package ru.practicum.ewm.main.server.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.event.EventFullDto;
import ru.practicum.ewm.main.api.event.EventShortDto;
import ru.practicum.ewm.main.api.event.EventSort;
import ru.practicum.ewm.main.server.model.EventGetAllParameters;
import ru.practicum.ewm.main.server.service.EventService;
import ru.practicum.ewm.util.aspect.log.ToLog;
import ru.practicum.ewm.util.validator.StartBeforeEndDateConstraint;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.util.constant.Constants.DATE_TIME_FORMAT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Validated
@ToLog
public class PublicEventController {
    private final EventService service;

    @GetMapping
    @StartBeforeEndDateConstraint
    public Collection<EventShortDto> getAll(
            @RequestParam(required = false, name = "rangeStart")
            @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime start,
            @RequestParam(required = false, name = "rangeEnd")
            @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime end,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(defaultValue = "EVENT_DATE") EventSort sort,
            @RequestParam(defaultValue = "0") @Min(0) int from,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            HttpServletRequest httpRequest) {
        return service.getAll(EventGetAllParameters.builder()
                        .start(start)
                        .end(end)
                        .text(text)
                        .categories(categories)
                        .paid(paid)
                        .onlyAvailable(onlyAvailable)
                        .eventSort(sort)
                        .from(from)
                        .size(size)
                        .httpServletRequest(httpRequest)
                .build());
    }

    @GetMapping("/{eventId}")
    public EventFullDto getById(@PathVariable @Min(1) long eventId, HttpServletRequest httpRequest) {
        return service.getByIdByPublic(eventId, httpRequest);
    }
}
