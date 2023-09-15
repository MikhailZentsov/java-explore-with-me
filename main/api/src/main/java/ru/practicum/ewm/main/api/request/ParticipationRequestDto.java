package ru.practicum.ewm.main.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.util.constant.Constants;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ParticipationRequestDto {
    private Long id;
    private Long requester;
    private Long event;
    private RequestStatus status;
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime created;
}
