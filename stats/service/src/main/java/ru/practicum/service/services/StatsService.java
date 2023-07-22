package ru.practicum.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.service.mappers.HitMapper;
import ru.practicum.service.models.Stats;
import ru.practicum.service.storages.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StatsService {

    private final StatsRepository repository;

    public HitDto create(HitDto hitDto) {
        return HitMapper.toDto(repository.save(HitMapper.toHit(hitDto)));
    }

    @Transactional(readOnly = true)
    public List<StatsDto> getAll(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<Stats> stats;
        if (unique) {
            stats = uris.isEmpty() ? repository.findByTimestampBetweenDistinct(start, end) :
                    repository.findByTimestampBetweenAndUriInDistinct(start, end, uris);
        } else {
            stats = uris.isEmpty() ? repository.findByTimestampBetween(start, end) :
                    repository.findByTimestampBetweenAndUriIn(start, end, uris);
        }
        return stats.stream().map(a -> new StatsDto(a.getApp(), a.getUri(), a.getHits())).collect(Collectors.toList());
    }
}
