package ru.practicum.main.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class AdminUserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto create(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("Получен запрос POST /admin/users/");
        return service.create(newUserRequest);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long userId) {
        log.info("Получен запрос DELETE /admin/users/ с userID {}", userId);
        service.delete(userId);
    }

    @GetMapping
    List<UserDto> getAll(@RequestParam(required = false) List<Long> ids,
                         @RequestParam(defaultValue = "0") int from,
                         @RequestParam(defaultValue = "10") int size) {
        log.info("Получен GET /admin/users с userId {}", ids);
        return service.getAll(ids, from, size);
    }
}
