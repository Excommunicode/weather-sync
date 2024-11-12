package ru.weathersync.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.weathersync.model.WeatherData;

import java.time.LocalDateTime;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    @Query("""
            SELECT w
            FROM WeatherData w
            WHERE w.region = :region
            AND w.createdAt BETWEEN :startDate AND :now
            """)
    Page<WeatherData> findAllByRegionAndCreatedAtBetween(
            @Param("region") String region,
            @Param("startDate") LocalDateTime startDate,
            @Param("now") LocalDateTime now,
            Pageable pageable);

}
