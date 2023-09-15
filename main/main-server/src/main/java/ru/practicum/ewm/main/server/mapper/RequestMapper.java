package ru.practicum.ewm.main.server.mapper;

import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main.api.event.RequestStatusUpdateResult;
import ru.practicum.ewm.main.api.request.ParticipationRequestDto;
import ru.practicum.ewm.main.api.request.RequestStatus;
import ru.practicum.ewm.main.server.entity.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "requester", source = "request.requester.id")
    @Mapping(target = "event", source = "request.event.id")
    @Mapping(target = "created", source = "request.createDate")
    ParticipationRequestDto toParticipationRequestDto(Request request);

    default RequestStatusUpdateResult toRequestStatusUpdateResult(List<Request> requests) {
        if (requests == null) {
            return null;
        }
        List<ParticipationRequestDto> participationRequestDtos = requests
                .stream()
                .map(this::toParticipationRequestDto)
                .collect(Collectors.toCollection(ArrayList<ParticipationRequestDto>::new));

        List<ParticipationRequestDto> confirmedRequests = participationRequestDtos
                .stream()
                .filter(this::isConfirmed)
                .collect(Collectors.toList());

        List<ParticipationRequestDto> rejectedRequests = participationRequestDtos
                .stream()
                .filter(this::isRejected)
                .collect(Collectors.toList());

        return RequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
    }

    private boolean isConfirmed(@NotNull ParticipationRequestDto dto) {
        return dto.getStatus().equals(RequestStatus.CONFIRMED);
    }

    private boolean isRejected(@NotNull ParticipationRequestDto dto) {
        return dto.getStatus().equals(RequestStatus.REJECTED);
    }
}
