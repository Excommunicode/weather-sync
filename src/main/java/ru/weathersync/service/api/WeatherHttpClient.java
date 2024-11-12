package ru.weathersync.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.weathersync.dto.WeatherDataDto;

public interface WeatherHttpClient {
    WeatherDataDto fetchWeatherData(double latitude, double longitude) throws JsonProcessingException;

    WeatherDataDto processResponse(String responseBody) throws JsonProcessingException;
}
