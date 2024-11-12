package ru.weathersync.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.weathersync.model.SaveWeatherDataEvent;
import ru.weathersync.model.WeatherData;
import ru.weathersync.repository.WeatherDataRepository;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class WeatherDataEventListener {
    private final WeatherDataRepository weatherDataRepository;


    @EventListener
    public void handleSaveWeatherDataEvent(SaveWeatherDataEvent event) {
        WeatherData weatherData = event.weatherData();
        weatherDataRepository.save(weatherData);
    }
}