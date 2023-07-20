package ru.practicum.service.storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.StatsDto;
import ru.practicum.service.models.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT uri, app, count(Distinct(ip)) hit " +
            "FROM stats " +
            "WHERE uri in :uris AND timestamp BETWEEN :start AND :end " +
            "GROUP BY uri, app",
            nativeQuery = true)
    List<StatsDto> findByTimestampBetweenAndUriInDistinct(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT uri, app, count(Distinct(ip)) hit " +
            "FROM stats " +
            "WHERE timestamp BETWEEN :start AND :end " +
            "GROUP BY uri, app",
            nativeQuery = true)
    List<StatsDto> findByTimestampBetweenDistinct(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT uri, app, count(ip) hit " +
            "FROM stats " +
            "WHERE uri in :uris AND timestamp BETWEEN :start AND :end " +
            "GROUP BY uri, app",
            nativeQuery = true)
    List<StatsDto> findByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT uri, app, count(ip) hit " +
            "FROM stats " +
            "WHERE timestamp BETWEEN :start AND :end " +
            "GROUP BY uri, app",
            nativeQuery = true)
    List<StatsDto> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
