package ru.practicum.ewm.statistics.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewm.statistics.dto.RequestHitDto;
import ru.practicum.ewm.statistics.dto.ResponseHitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.util.constant.Constants.DATE_TIME_FORMATTER;

public class StatisticsClient {
    private final WebClient webClient;
    private final String appName;

    public StatisticsClient(String serverUrl,
                            String appName) {
        this.webClient = WebClient.create(serverUrl);
        this.appName = appName;
    }

    public void postHit(HttpServletRequest httpServletRequest) {
        RequestHitDto hitDto = RequestHitDto.builder()
                .app(appName)
                .uri(httpServletRequest.getRequestURI())
                .ip(httpServletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();

        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/hit")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(hitDto)
                .retrieve()
                .bodyToMono(ResponseEntity.class)
                .block();
    }

    public List<ResponseHitDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hit")
                        .queryParam("start", start.format(DATE_TIME_FORMATTER))
                        .queryParam("end", end.format(DATE_TIME_FORMATTER))
                        .queryParam("uris", String.join(", ", uris))
                        .queryParam("unique", unique.toString())
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(ResponseHitDto.class)
                .collectList()
                .block();
    }
}
