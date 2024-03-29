package ru.practicum.ewm.util.constant;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class Constants {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public static final Sort SORT_BY_ID_ASC = Sort.by(Sort.Direction.ASC, "id");
    public static final String ONE_AS_STRING = "1";
    public static final String TWO_AS_STRING = "2";
}
