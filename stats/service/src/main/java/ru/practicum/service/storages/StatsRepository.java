package ru.practicum.service.storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.service.models.Hit;
import ru.practicum.service.models.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT new ru.practicum.dto.statsdto (app, uri, count(Distinct(ip)) as hits) " +
            "FROM stats " +
            "WHERE uri in :uris AND timestamp BETWEEN :start AND :end " +
            "GROUP BY uri, app",
            nativeQuery = true)
    List<Stats> findByTimestampBetweenAndUriInDistinct(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT uri, app, count(Distinct(ip)) hit " +
            "FROM stats " +
            "WHERE timestamp BETWEEN :start AND :end " +
            "GROUP BY uri, app",
            nativeQuery = true)
    List<Stats> findByTimestampBetweenDistinct(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT uri, app, count(ip) hit " +
            "FROM stats " +
            "WHERE uri in :uris AND timestamp BETWEEN :start AND :end " +
            "GROUP BY uri, app",
            nativeQuery = true)
    List<Stats> findByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT s.app, s.uri, count(s.ip) as hits " +
            "FROM stats s " +
            "WHERE timestamp BETWEEN :start AND :end " +
            "GROUP BY s.uri, s.app",
            nativeQuery = true)
    List<Stats> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
