package ru.practicum.service.service;

import ru.practicum.dto.EndPointHit;
import ru.practicum.dto.ViewStat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    EndPointHit create(EndPointHit endPointHit);

    List<ViewStat> getAll(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

    List<ViewStat> getAllForClient(List<String> uris);
}
