package ru.weathersync.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "weather_dates")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String region;

    private Double temperature;

    private Integer pressure;

    @Column(name = "wind_speed")
    private Double windSpeed;

    private Integer humidity;

    @Column(name = "feels_like")
    private Integer feelsLike;

    private String condition;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
