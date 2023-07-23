package ru.practicum.main.compilation.service;

import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationAdminRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto create(NewCompilationDto newCompilationDto);

    CompilationDto update(Long compilationId, UpdateCompilationAdminRequest request);

    void delete(Long compilationId);

    CompilationDto get(Long compilationId);

    List<CompilationDto> getAll(boolean pinned, int from, int size);
}
