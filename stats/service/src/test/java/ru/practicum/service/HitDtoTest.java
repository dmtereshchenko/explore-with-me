package ru.practicum.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.Constant;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class HitDtoTest {

    @Autowired
    private JacksonTester<HitDto> json;

    @Test
    void serializeTest() throws Exception {
        HitDto hitDto = new HitDto(1L, "ewm-main-service", "events/1", "192.163.0.1", LocalDateTime.of(2022, 9, 6, 11, 00, 23));
        JsonContent<HitDto> result = json.write(hitDto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.app");
        assertThat(result).hasJsonPath("$.uri");
        assertThat(result).hasJsonPath("$.ip");
        assertThat(result).hasJsonPath("$.timestamp");
        assertThat(result).extractingJsonPathValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo(hitDto.getApp());
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo(hitDto.getUri());
        assertThat(result).extractingJsonPathStringValue("$.ip").isEqualTo(hitDto.getIp());
        assertThat(result).extractingJsonPathStringValue("$.timestamp").isEqualTo(hitDto.getTimestamp().format(DateTimeFormatter.ofPattern(Constant.DTFormat)));
    }
}
