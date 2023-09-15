package ru.practicum.ewm.main.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.statistics.client.StatisticsClient;

@Configuration
public class BeanStatClientConfig {
    private final String serverUrl;
    private final String appName;

    public BeanStatClientConfig(@Value("${statistics.server.address}") String serverUrl,
                                @Value("${application.name}") String appName) {
        this.serverUrl = serverUrl;
        this.appName = appName;
    }

    @Bean
    public StatisticsClient createStatisticClient() {
        return new StatisticsClient(serverUrl, appName);
    }
}
