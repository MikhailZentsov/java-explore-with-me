package ru.practicum.ewm.main.api.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.main.api.request.RequestStatus;
import ru.practicum.ewm.util.validator.EnumAllowedConstraint;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RequestStatusUpdateRequest {

    @Size(min = 1)
    private List<Long> requestIds;

    @EnumAllowedConstraint(enumClass = RequestStatus.class, allowed = {"CONFIRMED", "REJECTED"})
    private RequestStatus status;
}
