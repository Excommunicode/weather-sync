package ru.weathersync.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum City {
    DUBAI(25.2048, 55.2708, "Дубай"),
    ANTARCTICA(-90.0000, 0.0000, "Антарктида"),
    MOSCOW(55.7558, 37.6176, "Москва"),
    NEPAL(28.3949, 84.1240, "Непал"),
    NEW_YORK(40.7128, -74.0060, "Нью-Йорк"),
    ALMATY(43.2220, 76.8512, "Asia/Almaty"),
    NOVOSIBIRSK(55.0084, 82.9357, "Новосибирск");

    private final double latitude;
    private final double longitude;
    private final String displayName;
}
