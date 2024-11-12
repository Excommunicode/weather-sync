package ru.weathersync.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.weathersync.dto.WeatherDataDto;
import ru.weathersync.mapper.WeatherDataMapper;
import ru.weathersync.model.City;
import ru.weathersync.model.WeatherData;
import ru.weathersync.repository.WeatherDataRepository;
import ru.weathersync.service.api.WeatherDataService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherDataServiceImpl implements WeatherDataService {

    private final WeatherHttpClientImpl weatherHttpClient;
    private final WeatherDataMapper weatherDataMapper;
    private final WeatherDataRepository weatherDataRepository;

    @Override
    public WeatherDataDto getWeatherData(double latitude, double longitude) throws JsonProcessingException {
        return weatherHttpClient.fetchWeatherData(latitude, longitude);
    }

    @Override
    public WeatherDataDto getWeatherData(String city) throws JsonProcessingException {
        System.out.println(city);
        City cityEnum = City.valueOf(normalize(city));
        return weatherHttpClient.fetchWeatherData(cityEnum.getLatitude(), cityEnum.getLongitude());
    }

    @Override
    public List<WeatherDataDto> getAllByRegionAndTenDays(String region, Pageable pageable) {
        LocalDateTime now = LocalDateTime.now().minusSeconds(10);
        LocalDateTime startDate = now.minusDays(10);

        List<WeatherData> weatherDataList = weatherDataRepository.findAllByRegionAndCreatedAtBetween(region, startDate, now, pageable).getContent();
        return weatherDataMapper.toPageDto(weatherDataList);
    }

    private String normalize(String cityName) {
        return cityName.trim().replaceAll("[\\s-]+", "_").toUpperCase();
    }
}
