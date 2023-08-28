package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.RequestHtiDto;
import ru.practicum.model.HitEntity;

@UtilityClass
public class HitMapper {
    public HitEntity toHitEntityFromRequestHitDto(RequestHtiDto hitDto) {
        return HitEntity.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .request_time(hitDto.getTimestamp())
                .build();
    }
}
