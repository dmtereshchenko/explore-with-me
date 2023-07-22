package ru.practicum.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.dto.StatsDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class StatsDtoTest {

    @Autowired
    private JacksonTester<StatsDto> json;

    @Test
    void serializeTest() throws Exception {
        StatsDto statsDto = new StatsDto("ewm-main-service", "events/1", 9L);
        JsonContent<StatsDto> result = json.write(statsDto);
        assertThat(result).hasJsonPath("$.app");
        assertThat(result).hasJsonPath("$.uri");
        assertThat(result).hasJsonPath("$.hits");
        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo(statsDto.getApp());
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo(statsDto.getUri());
        assertThat(result).extractingJsonPathValue("$.hits").isEqualTo(9);
    }
}
