package ru.practicum.ewm.main.api.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.util.constant.Constants;
import ru.practicum.ewm.util.validator.DateAfterValueHourFutureConstraint;
import ru.practicum.ewm.util.validator.EnumAllowedConstraint;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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

    @EnumAllowedConstraint(enumClass = StateAction.class, allowed = {"SEND_TO_REVIEW", "CANCEL_REVIEW"})
    private StateAction stateAction;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    @DateAfterValueHourFutureConstraint(value = Constants.TWO_AS_STRING)
    private LocalDateTime eventDate;
}
