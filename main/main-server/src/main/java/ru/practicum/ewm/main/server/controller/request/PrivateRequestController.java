package ru.practicum.ewm.main.server.controller.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.request.ParticipationRequestDto;
import ru.practicum.ewm.main.server.service.RequestService;
import ru.practicum.ewm.util.aspect.log.ToLog;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
@Validated
@ToLog
public class PrivateRequestController {
    private final RequestService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@RequestParam @Min(1) long eventId, @PathVariable @Min(1) long userId) {
        return service.create(eventId, userId);
    }

    @GetMapping
    public List<ParticipationRequestDto> getAll(@PathVariable @Min(1) long userId) {
        return service.getAll(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable @Min(1) long requestId, @PathVariable @Min(1) long userId) {
        return service.cancel(requestId, userId);
    }
}
