package ru.practicum.service;

import ru.practicum.dto.RequestHtiDto;
import ru.practicum.dto.ResponseHitDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {

    void createHit(RequestHtiDto hitDto);

    List<ResponseHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
