package ru.practicum.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Constant;
import ru.practicum.dto.EndPointHit;
import ru.practicum.dto.ViewStat;
import ru.practicum.service.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndPointHit create(@RequestBody EndPointHit endPointHit) {
        log.info("Получен запрос POST /hit/");
        return service.create(endPointHit);
    }

    @GetMapping("/stats")
    public List<ViewStat> getAll(@DateTimeFormat(pattern = Constant.DTFormat) LocalDateTime start,
                                 @DateTimeFormat(pattern = Constant.DTFormat) LocalDateTime end,
                                 @RequestParam(required = false) List<String> uris,
                                 @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Получен запрос GET /stats/");
        return service.getAll(start, end, uris, unique);
    }

    @GetMapping("/client/stats")
    public List<ViewStat> getAllFromClient(@RequestParam(required = false) List<String> uris) {
        log.info("Получен запрос GET /client/stats/");
        return service.getAllForClient(uris);
    }
}
