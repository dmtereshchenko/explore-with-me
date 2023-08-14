package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.service.CompilationService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class PublicCompilationController {

    private final CompilationService service;

    @GetMapping("/{compilationId}")
    CompilationDto get(@PathVariable Long compilationId) {
        log.info("Получен запрос GET /compilations/{}/", compilationId);
        return service.get(compilationId);
    }

    @GetMapping
    List<CompilationDto> getAll(@RequestParam(defaultValue = "false") boolean pinned,
                                @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "10") int size) {
        log.info("Получен запрос GET /compilations/");
        return service.getAll(pinned, from, size);
    }
}
