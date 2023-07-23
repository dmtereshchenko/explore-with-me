package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationAdminRequest;
import ru.practicum.main.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {

    private final CompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Получен запрос POST /admin/compilations/");
        return service.create(newCompilationDto);
    }

    @PatchMapping("/{compilationId}")
    CompilationDto update(@PathVariable Long compilationId,
                          @Valid @RequestBody UpdateCompilationAdminRequest request) {
        log.info("Получен новый запрос PATCH /admin/compilations/{}/", compilationId);
        return service.update(compilationId, request);
    }

    @DeleteMapping("/{compilationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long compilationId) {
        log.info("Получен запрос DELETE /admin/compilations/{}/", compilationId);
        service.delete(compilationId);
    }
}
