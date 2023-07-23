package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {

    private final CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto create(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Получен запрос POST /admin/categories/");
        return service.create(newCategoryDto);
    }

    @PatchMapping("/{categoryId}")
    CategoryDto update(@PathVariable Long categoryId,
                       @Valid @RequestBody CategoryDto categoryDto) {
        log.info("Получен запрос PATCH /admin/categories/{}/", categoryId);
        return service.update(categoryId, categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long categoryId) {
        log.info("Получен запрос DELETE /admin/categories/{}", categoryId);
        service.delete(categoryId);
    }
}
