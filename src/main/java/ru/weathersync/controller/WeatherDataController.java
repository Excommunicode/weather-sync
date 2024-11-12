package ru.weathersync.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Weather Data", description = "Контроллер для работы с данными о погоде")
public class WeatherDataController {

    private final WeatherDataService weatherDataService;

    @GetMapping("/{city}")
    @Operation(summary = "Получение погоды по городу", description = "Возвращает текущие данные о погоде для указанного города")
    public WeatherDataDto getWeatherData(
            @Parameter(description = "Название города", example = "Almaty")
            @PathVariable String city) throws JsonProcessingException {
        return weatherDataService.getWeatherData(city);
    }

    @GetMapping
    @Operation(summary = "Получение погоды по координатам", description = "Возвращает текущие данные о погоде для указанных координат")
    public WeatherDataDto getWeatherByCoordinates(
            @Parameter(description = "Широта", example = "43.25654")
            @RequestParam double lat,
            @Parameter(description = "Долгота", example = "76.92848") 
            @RequestParam double lon) throws JsonProcessingException {
        return weatherDataService.getWeatherData(lat, lon);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение погоды за последние 10 дней", description = "Возвращает данные о погоде за последние 10 дней для указанного города")
    public List<WeatherDataDto> getWeatherDataForLast10Days(
            @Parameter(description = "Название города", example = "Almaty")
            @RequestParam String city,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return weatherDataService.getAllByRegionAndTenDays(city, pageable);
    }
}