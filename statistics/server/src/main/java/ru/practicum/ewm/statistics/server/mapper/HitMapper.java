package ru.practicum.ewm.statistics.server.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.statistics.server.model.HitEntity;
import ru.practicum.ewm.statistics.dto.RequestHitDto;

@UtilityClass
public class HitMapper {
    public HitEntity toHitEntityFromRequestHitDto(RequestHitDto hitDto) {
        return HitEntity.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(hitDto.getTimestamp())
                .build();
    }

    public RequestHitDto toRequestHitDto(HitEntity entity) {
        return RequestHitDto.builder()
                .app(entity.getApp())
                .uri(entity.getUri())
                .ip(entity.getIp())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
