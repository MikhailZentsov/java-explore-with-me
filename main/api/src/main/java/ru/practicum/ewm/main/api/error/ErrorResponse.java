package ru.practicum.ewm.main.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.util.constant.Constants;

import java.time.LocalDateTime;


@AllArgsConstructor
@Value
@Builder
public class ErrorResponse {

    int status;

    String reason;

    String message;

    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    LocalDateTime timestamp;
}
