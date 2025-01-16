package ru.liga.parcelmanager.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramProperties {

    private String token;
    private String name;
}
