package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import ru.practicum.aspect.log.ToLog;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.service.StatisticService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.constant.Constants.DATE_TIME_FORMAT;

@RestController
@RequiredArgsConstructor
@ToLog
public class StatisticsController {
    private final StatisticService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createHit(@RequestBody RequestHitDto hitDto) {
        service.createHit(hitDto);
    }

    @GetMapping("/stats")
    public List<ResponseHitDto> getStats(@RequestParam(name = "start") @DateTimeFormat(fallbackPatterns = DATE_TIME_FORMAT) LocalDateTime start,
                                         @RequestParam(name = "end") @DateTimeFormat(fallbackPatterns = DATE_TIME_FORMAT) LocalDateTime end,
                                         @RequestParam(name = "uris", required = false) List<String> uris,
                                         @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        return service.getStats(start, end, uris, unique);
    }
}
