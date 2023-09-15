package ru.practicum.ewm.main.api.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventUpdateRequest {

    private String title;

    private String annotation;

    private Long category;

    private String description;

    private LocationDto location;

    private Boolean paid;

    private Boolean requestModeration;

    private Long participantLimit;

    private LocalDateTime eventDate;

    private StateAction stateAction;
}
