package ru.practicum.ewm.main.api.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.util.validator.DateAfterValueHourFutureConstraint;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.ewm.util.constant.Constants.DATE_TIME_FORMAT;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EventUpdateUserRequest extends EventUpdateRequest {

    @Size(min = 3, max = 120)
    private String title;

    @Size(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    private LocationDto location;

    private Boolean paid;

    private Boolean requestModeration;

    private Long participantLimit;

    private StateAction stateAction;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    @DateAfterValueHourFutureConstraint(value = "2")
    private LocalDateTime eventDate;
}
