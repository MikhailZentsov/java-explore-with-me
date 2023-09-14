package ru.practicum.ewm.statistics.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.ewm.statistics.dto.RequestHitDto;
import ru.practicum.ewm.statistics.dto.ResponseHitDto;
import ru.practicum.ewm.statistics.server.service.StatisticService;
import ru.practicum.ewm.util.aspect.log.ToLog;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.util.constant.Constants.DATE_TIME_FORMAT;

@RestController
@RequiredArgsConstructor
@Validated
@ToLog
public class StatisticsController {
    private final StatisticService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestHitDto createHit(@RequestBody @Valid RequestHitDto hitDto) {
        return service.createHit(hitDto);
    }

    @GetMapping("/stats")
    public Collection<ResponseHitDto> getStats(@RequestParam @DateTimeFormat(fallbackPatterns = DATE_TIME_FORMAT) LocalDateTime start,
                                               @RequestParam @DateTimeFormat(fallbackPatterns = DATE_TIME_FORMAT) LocalDateTime end,
                                               @RequestParam(required = false) List<String> uris,
                                               @RequestParam(defaultValue = "false") boolean unique) {
        if (start.isAfter(end)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End cannot be early then start");
        }
        return service.getStats(start, end, uris, unique);
    }
}
