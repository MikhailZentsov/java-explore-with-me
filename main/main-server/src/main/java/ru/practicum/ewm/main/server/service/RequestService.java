package ru.practicum.ewm.main.server.service;

import ru.practicum.ewm.main.api.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto create(long eventId, long userId);

    List<ParticipationRequestDto> getAll(long userId);

    ParticipationRequestDto cancel(long requestId, long userId);
}
