package ru.practicum.main.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationAdminRequest;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.storage.CompilationRepository;
import ru.practicum.main.event.storage.EventRepository;
import ru.practicum.main.exception.CompilationNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        return CompilationMapper.toDto(compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto,
                newCompilationDto.getEvents() == null ? new ArrayList<>() : eventRepository.findAllById(newCompilationDto.getEvents()))));
    }

    @Override
    public CompilationDto update(Long compilationId, UpdateCompilationAdminRequest request) {
        return CompilationMapper.toDto(compilationRepository.save(CompilationMapper.toCompilationUpdated(
                compilationRepository.findById(compilationId).orElseThrow(() -> new CompilationNotFoundException(compilationId)),
                request,
                request.getEvents() == null ? new ArrayList<>() : eventRepository.findAllById(request.getEvents())
        )));
    }

    @Override
    public void delete(Long compilationId) {
        compilationRepository.delete(compilationRepository.findById(compilationId).orElseThrow(() -> new CompilationNotFoundException(compilationId)));
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto get(Long compilationId) {
        return CompilationMapper.toDto(compilationRepository.findById(compilationId).orElseThrow(() -> new CompilationNotFoundException(compilationId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getAll(boolean pinned, int from, int size) {
        return compilationRepository.findCompilationsByPinned(pinned, PageRequest.of(from / size, size)).stream()
                .map(CompilationMapper::toDto)
                .collect(Collectors.toList());
    }
}
