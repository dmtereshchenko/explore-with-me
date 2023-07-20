package ru.practicum.service;

import org.junit.jupiter.api.Test;
import ru.practicum.dto.HitDto;
import ru.practicum.service.mappers.HitMapper;
import ru.practicum.service.models.Hit;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HitMapperTest {

    private final HitDto hitDto = new HitDto(1L, "ewm-main-service", "events/1", "192.163.0.1", LocalDateTime.of(2022, 9, 6, 11, 00, 23));
    private final Hit hit = new Hit(1L, "ewm-main-service", "events/1", "192.163.0.1", LocalDateTime.of(2022, 9, 6, 11, 00, 23));

    @Test
    void toDtoTest() {
        assertEquals(hitDto, HitMapper.toDto(hit));
    }

    @Test
    void toHitTest() {
        assertEquals(hit, HitMapper.toHit(hitDto));
    }
}
