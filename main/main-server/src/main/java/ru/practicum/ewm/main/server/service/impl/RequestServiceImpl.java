package ru.practicum.ewm.main.server.service.impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.api.event.EventStatus;
import ru.practicum.ewm.main.api.request.ParticipationRequestDto;
import ru.practicum.ewm.main.api.request.RequestStatus;
import ru.practicum.ewm.main.server.entity.Event;
import ru.practicum.ewm.main.server.entity.QRequest;
import ru.practicum.ewm.main.server.entity.Request;
import ru.practicum.ewm.main.server.entity.User;
import ru.practicum.ewm.main.server.mapper.RequestMapper;
import ru.practicum.ewm.main.server.repository.EventRepository;
import ru.practicum.ewm.main.server.repository.RequestRepository;
import ru.practicum.ewm.main.server.repository.UserRepository;
import ru.practicum.ewm.main.server.service.RequestService;
import ru.practicum.ewm.main.util.exception.AlreadyExistsException;
import ru.practicum.ewm.main.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ru.practicum.ewm.util.constant.Constants.SORT_BY_ID_ASC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestMapper mapper;

    @Override
    @Transactional
    public ParticipationRequestDto create(long eventId, long userId) {
        User user = getUserById(userId);
        Event event = getEventById(eventId);

        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new AlreadyExistsException("Event with ID = " + eventId + " does not PUBLISHED.");
        }
        if (event.getInitiator().getId().equals(userId)) {
            throw new AlreadyExistsException("Event with ID = " + eventId + " is your event.");
        }
        if (event.getParticipantLimit() > 0 && event.getParticipantLimit() - event.getConfirmedRequests() <= 0) {
            throw new AlreadyExistsException("The limit of requests to participate in the event has been reached.");
        }

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QRequest.request.event.id.eq(eventId));
        builder.and(QRequest.request.requester.id.eq(userId));
        if (requestRepository.count(builder) > 0) {
            throw new AlreadyExistsException("Request from you already exists.");
        }

        Request request = makeRequest(event, user);

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
        }

        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        eventRepository.save(event);

        return mapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getAll(long userId) {
        User user = getUserById(userId);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QRequest.request.requester.eq(user));

        return StreamSupport.stream(requestRepository.findAll(builder, SORT_BY_ID_ASC).spliterator(), false)
                .map(mapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(long requestId, long userId) {
        getUserById(userId);
        Request request = getRequestById(requestId);

        if (request.getRequester().getId() != userId) {
            throw new NotFoundException("Request with ID = " + requestId + " does not exists");
        }

        request.setStatus(RequestStatus.CANCELED);

        return mapper.toParticipationRequestDto(requestRepository.save(request));
    }

    public Request makeRequest(Event event, User user) {
        return Request.builder()
                .requester(user)
                .createDate(LocalDateTime.now())
                .event(event)
                .status(RequestStatus.PENDING)
                .build();
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with ID = " + userId + " does not exists.")
        );
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Event with ID = " + eventId + " does not exists")
        );
    }

    private Request getRequestById(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException("Request with ID = " + requestId + " does not exists")
        );
    }
}
