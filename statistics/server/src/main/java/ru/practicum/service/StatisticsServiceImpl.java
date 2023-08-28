package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.RequestHtiDto;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.repository.StatisticsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticsServiceImpl implements StatisticService {
    private final StatisticsRepository repository;

    @Override
    public void createHit(RequestHtiDto hitDto) {
        repository.save(HitMapper.toHitEntityFromRequestHitDto(hitDto));
    }

    @Override
    public List<ResponseHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        return repository.getStats(start, end, uris, unique);
    }
}
