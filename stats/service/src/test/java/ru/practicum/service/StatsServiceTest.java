package ru.practicum.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.HitDto;
import ru.practicum.service.mappers.HitMapper;
import ru.practicum.service.models.Hit;
import ru.practicum.service.services.StatsService;
import ru.practicum.service.storages.StatsRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    private final HitDto hitDto = new HitDto(1L, "ewm-main-service", "events/1", "192.163.0.1", LocalDateTime.of(2022, 9, 6, 11, 00, 23));
    private final Hit hit = HitMapper.toHit(hitDto);

    @InjectMocks
    StatsService service;
    @Mock
    private StatsRepository repository;

    @Test
    void createTest() {
        when(repository.save(any())).thenReturn(hit);
        assertEquals(hitDto, service.create(hitDto));
        verify(repository).save(any());
    }
}
