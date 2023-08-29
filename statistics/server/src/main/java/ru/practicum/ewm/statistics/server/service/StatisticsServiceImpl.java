package ru.practicum.ewm.statistics.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.statistics.dto.RequestHitDto;
import ru.practicum.ewm.statistics.dto.ResponseHitDto;
import ru.practicum.ewm.statistics.server.mapper.HitMapper;
import ru.practicum.ewm.statistics.server.repository.StatisticsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticsServiceImpl implements StatisticService {
    private final StatisticsRepository repository;

    @Override
    public void createHit(RequestHitDto hitDto) {
        repository.save(HitMapper.toHitEntityFromRequestHitDto(hitDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        return repository.getStats(start, end, uris, unique);
    }
}
