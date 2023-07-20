package ru.practicum.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.StatsDto;
import ru.practicum.service.storages.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsRepositoryTest {

    private final StatsDto statsDto = (new StatsDto("ewm-main-service", "events/1", 9));
    private final List<StatsDto> stats = List.of(statsDto);
    @Mock
    private StatsRepository repository;

    @Test
    void findByTimestampBetweenAndUriInDistinctTest() {
        when(repository.findByTimestampBetweenAndUriInDistinct(any(LocalDateTime.class), any(LocalDateTime.class), anyList()))
                .thenReturn(stats);
        assertEquals(stats, repository.findByTimestampBetweenAndUriInDistinct(LocalDateTime.now(), LocalDateTime.now(), List.of("uri")));
    }

    @Test
    void findByTimestampBetweenDistinctTest() {
        when(repository.findByTimestampBetweenDistinct(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(stats);
        assertEquals(stats, repository.findByTimestampBetweenDistinct(LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    void findByTimestampBetweenAndUriInTest() {
        when(repository.findByTimestampBetweenAndUriIn(any(LocalDateTime.class), any(LocalDateTime.class), anyList()))
                .thenReturn(stats);
        assertEquals(stats, repository.findByTimestampBetweenAndUriIn(LocalDateTime.now(), LocalDateTime.now(), List.of("uri")));
    }

    @Test
    void findByTimestampBetweenTest() {
        when(repository.findByTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(stats);
        assertEquals(stats, repository.findByTimestampBetween(LocalDateTime.now(), LocalDateTime.now()));
    }
}
