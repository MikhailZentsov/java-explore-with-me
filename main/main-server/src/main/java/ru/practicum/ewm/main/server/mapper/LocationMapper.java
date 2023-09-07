package ru.practicum.ewm.main.server.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.api.event.LocationDto;
import ru.practicum.ewm.main.server.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocation(LocationDto locationDto);
}
