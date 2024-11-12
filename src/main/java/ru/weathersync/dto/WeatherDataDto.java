package ru.weathersync.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WeatherDataDto {
    private String region;
    private Double temperature;
    private Integer pressure;
    private Double windSpeed;
    private Integer humidity;
    private Integer feelsLike;
    private String condition;
    private String message;
    private String createdAt;
}
