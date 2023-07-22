package ru.practicum.service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Constant;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.service.services.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto create(@RequestBody HitDto hitDto) {
        log.info("Получен запрос POST /hit/");
        return service.create(hitDto);
    }

    @GetMapping("/stats")
    public List<StatsDto> getAll(@DateTimeFormat(pattern = Constant.DTFormat) LocalDateTime start,
                                 @DateTimeFormat(pattern = Constant.DTFormat) LocalDateTime end,
                                 @RequestParam(name = "uris", defaultValue = "") List<String> uris,
                                 @RequestParam(name = "unique", defaultValue = "False") Boolean unique) {
        log.info("Получен запрос GET /stats/");
        return service.getAll(start, end, uris, unique);
    }
}
