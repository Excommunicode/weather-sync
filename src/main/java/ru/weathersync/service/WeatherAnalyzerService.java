package ru.weathersync.service;

import org.springframework.stereotype.Component;
import ru.weathersync.model.WeatherData;

@Component
public class WeatherAnalyzerService {

    public String analyze(WeatherData weatherData) {
        StringBuilder message = new StringBuilder();
        boolean hasMessage = false;

        Double temperature = weatherData.getTemperature();
        if (temperature != null) {
            if (temperature < -30) {
                message.append("Внимание: зафиксирован аномальный холод. Рекомендуется оставаться в помещении и утепляться.\n");
                hasMessage = true;
            } else if (temperature < 0) {
                message.append("Холодная погода. Одевайтесь тепло и будьте осторожны на дороге.\n");
                hasMessage = true;
            } else if (temperature > 35) {
                message.append("Аномальная жара. Пейте больше воды.\n");
                hasMessage = true;
            }
        }
        if (!hasMessage) {
            message.append("Погода в норме. Наслаждайтесь днем!\n");
        }
        return message.toString().trim();
    }
}
