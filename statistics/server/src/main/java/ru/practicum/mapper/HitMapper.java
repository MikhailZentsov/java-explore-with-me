package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.model.HitEntity;

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
}
