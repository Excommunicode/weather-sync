package ru.weathersync.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import ru.weathersync.dto.WeatherDataDto;

import java.util.List;

public interface WeatherDataService {
    WeatherDataDto getWeatherData(double latitude, double longitude) throws JsonProcessingException;

    WeatherDataDto getWeatherData(String city) throws JsonProcessingException;

    List<WeatherDataDto> getAllByRegionAndTenDays(String region, Pageable pageable);
}