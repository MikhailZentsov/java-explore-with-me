package ru.practicum.ewm.main.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.main.api.event.EventSort;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
public class EventGetAllParameters {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private LocalDateTime start;
    private LocalDateTime end;
    private Boolean onlyAvailable;
    private EventSort eventSort;
    private int from;
    private int size;
    private HttpServletRequest httpServletRequest;
}
