package ru.practicum.ewm.statistics.server.service;

import ru.practicum.ewm.statistics.dto.RequestHitDto;
import ru.practicum.ewm.statistics.dto.ResponseHitDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {

    void createHit(RequestHitDto hitDto);

    List<ResponseHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
