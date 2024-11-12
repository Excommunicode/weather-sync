package ru.weathersync.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.weathersync.dto.WeatherDataDto;
import ru.weathersync.mapper.WeatherDataMapper;
import ru.weathersync.model.SaveWeatherDataEvent;
import ru.weathersync.model.WeatherData;
import ru.weathersync.service.api.WeatherHttpClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WeatherHttpClientImpl implements WeatherHttpClient {

    @Value("${yandex.weather.url}")
    private String url;

    @Value("${access.key}")
    private String accessKey;

    @Value("${http.header}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final WeatherDataMapper weatherDataMapper;
    private final WeatherAnalyzerService weatherAnalyzerService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public WeatherDataDto fetchWeatherData(double latitude, double longitude) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(apiKey, accessKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String requestUrl = String.format("%s?lat=%f&lon=%f", url, latitude, longitude);
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);

        return processResponse(response.getBody());
    }

    @Override
    public WeatherDataDto processResponse(String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode factNode = rootNode.path("fact");
        JsonNode tzinfoNode = rootNode.path("info").path("tzinfo");
        JsonNode infoNode = rootNode.path("info");


        String cityNameFromJson = tzinfoNode.path("name").asText();

        WeatherData weatherData = weatherDataMapper.toEntity(
                factNode.path("condition").asText(),
                cityNameFromJson,
                factNode.path("temp").asDouble(),
                factNode.path("feels_like").asInt(),
                factNode.path("wind_speed").asDouble(),
                infoNode.path("def_pressure_mm").asInt(),
                factNode.path("humidity").asInt(),
                LocalDateTime.now()
        );

        eventPublisher.publishEvent(new SaveWeatherDataEvent(weatherData));
        return weatherDataMapper.toDto(weatherData, weatherAnalyzerService.analyze(weatherData));
    }
}

