package ru.practicum.ewm.main.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

import static ru.practicum.ewm.util.constant.Constants.DATE_TIME_FORMAT;


@AllArgsConstructor
@Value
@Builder
public class ErrorResponse {

    int status;

    String reason;

    String message;

    @JsonFormat(pattern= DATE_TIME_FORMAT)
    LocalDateTime timestamp;
}
