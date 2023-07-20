package ru.practicum.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.service.controllers.StatsController;
import ru.practicum.service.services.StatsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StatsControllerTest {

    @MockBean
    private final StatsService service;
    private final MockMvc mvc;
    private final ObjectMapper mapper;

    private final HitDto hitDto = new HitDto(1L, "ewm-main-service", "events/1", "192.163.0.1", LocalDateTime.of(2022, 9, 6, 11, 00, 23));
    private final StatsDto statsDto = (new StatsDto("ewm-main-service", "events/1", 9));
    private final List<StatsDto> stats = List.of(statsDto);

    @Test
    void createTest() throws Exception {
        when(service.create(any())).thenReturn(hitDto);
        mvc.perform(post("/hit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(hitDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(hitDto.getId()))
                .andExpect(jsonPath("$.app").value(hitDto.getApp()))
                .andExpect(jsonPath("$.uri").value(hitDto.getUri()))
                .andExpect(jsonPath("$.ip").value(hitDto.getIp()))
                .andExpect(jsonPath("$.timestamp").value(hitDto.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    @Test
    void getAllTest() throws Exception {
        when(service.getAll(any(LocalDateTime.class), any(LocalDateTime.class), any(), any())).thenReturn(stats);
        mvc.perform(get("/stats?start=2022-01-01 00:00:00&end=2022-12-31 23:59:59&unique=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(stats)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].app").value(statsDto.getApp()))
                .andExpect(jsonPath("$[0].uri").value(statsDto.getUri()))
                .andExpect(jsonPath("$[0].hits").value(statsDto.getHits()));
    }
}
