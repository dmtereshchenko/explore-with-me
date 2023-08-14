package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.storage.CategoryRepository;
import ru.practicum.main.event.storage.EventRepository;
import ru.practicum.main.exception.CategoryNotFoundException;
import ru.practicum.main.exception.SomethingWentWrongException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        if (categoryRepository.existsByName(newCategoryDto.getName()))
            throw new SomethingWentWrongException("Невозможно создать категорию с уже существующим названием.");
        return CategoryMapper.toDto(categoryRepository.save(CategoryMapper.toCategory(newCategoryDto)));
    }

    @Override
    public CategoryDto update(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        if (categoryRepository.existsByName(categoryDto.getName()) && !categoryDto.getName().equals(category.getName())) {
            throw new SomethingWentWrongException("Невозможно изменить название на уже существующее.");
        }
        category.setName(categoryDto.getName());
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void delete(Long categoryId) {
        if (!eventRepository.findEventsByCategoryId(categoryId).isEmpty())
            throw new SomethingWentWrongException("Невозможно удалить категорию, в которой есть созданные события.");
        categoryRepository.delete(categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId)));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto get(Long categoryId) {
        return CategoryMapper.toDto(categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAll(int from, int size) {
        return categoryRepository.findAll(PageRequest.of(from / size, size)).stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
