package ru.weathersync.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.weathersync.dto.WeatherDataDto;
import ru.weathersync.model.WeatherData;

import java.time.LocalDateTime;
import java.util.List;

import static ru.weathersync.constant.Constant.DATE_TIME_FORMATTER;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeatherDataMapper {


    WeatherData toEntity(String condition, String region, Double temperature, Integer feelsLike,
                         Double windSpeed, Integer pressure, Integer humidity, LocalDateTime createdAt);

    @Mappings({
            @Mapping(target = "createdAt", source = "weatherData.createdAt", dateFormat = DATE_TIME_FORMATTER)
    })
    WeatherDataDto toDto(WeatherData weatherData, String message);

    List<WeatherDataDto> toPageDto(List<WeatherData> weatherDataPage);
}