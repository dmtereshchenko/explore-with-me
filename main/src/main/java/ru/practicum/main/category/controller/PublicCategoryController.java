package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class PublicCategoryController {

    private final CategoryService service;

    @GetMapping("/{categoryId}")
    CategoryDto get(@PathVariable Long categoryId) {
        log.info("Получен запрос GET /categories/{}", categoryId);
        return service.get(categoryId);
    }

    @GetMapping
    List<CategoryDto> getAll(@RequestParam(defaultValue = "0") int from,
                             @RequestParam(defaultValue = "10") int size) {
        log.info("Получен запрос GET /categories/");
        return service.getAll(from, size);
    }
}
