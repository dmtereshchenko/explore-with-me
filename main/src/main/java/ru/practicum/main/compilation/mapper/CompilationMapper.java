package ru.practicum.main.compilation.mapper;

import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationAdminRequest;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(
                null,
                newCompilationDto.getTitle(),
                newCompilationDto.isPinned(),
                events
        );
    }

    public static CompilationDto toDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.isPinned(),
                compilation.getEvents() == null ? new ArrayList<>() : compilation.getEvents().stream()
                        .map(EventMapper::toShortDto)
                        .collect(Collectors.toList())
        );
    }

    public static Compilation toCompilationUpdated(Compilation compilation, UpdateCompilationAdminRequest request, List<Event> events) {
        return new Compilation(
                compilation.getId(),
                Objects.requireNonNullElse(request.getTitle(), compilation.getTitle()),
                Objects.requireNonNullElse(request.getPinned(), compilation.isPinned()),
                events.isEmpty() ? compilation.getEvents() : events
        );
    }
}
