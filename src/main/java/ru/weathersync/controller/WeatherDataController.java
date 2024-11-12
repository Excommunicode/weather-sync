package ru.weathersync.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.weathersync.dto.WeatherDataDto;
import ru.weathersync.service.api.WeatherDataService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/weather")
public class WeatherDataController {

    private final WeatherDataService weatherDataService;


    @GetMapping("/{city}")
    public WeatherDataDto getWeatherData(@PathVariable String city) throws JsonProcessingException {
        return weatherDataService.getWeatherData(city);
    }

    @GetMapping
    public WeatherDataDto getWeatherByCoordinates(@RequestParam double lat, @RequestParam double lon) throws JsonProcessingException {
        return weatherDataService.getWeatherData(lat, lon);
    }

    @GetMapping("/all")
    public List<WeatherDataDto> getWeatherDataForLast10Days(
            @RequestParam String city,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return weatherDataService.getAllByRegionAndTenDays(city, pageable);
    }
}
