package ru.practicum.service.mapper;

import ru.practicum.Constant;
import ru.practicum.dto.EndPointHit;
import ru.practicum.service.model.Hit;

import java.time.LocalDateTime;

public class HitMapper {

    public static Hit toHit(EndPointHit endPointHit) {
        return new Hit(
                endPointHit.getId(),
                endPointHit.getApp(),
                endPointHit.getUri(),
                endPointHit.getIp(),
                LocalDateTime.parse(endPointHit.getTimestamp().format(Constant.FORMATTER), Constant.FORMATTER)
        );
    }

    public static EndPointHit toDto(Hit hit) {
        return new EndPointHit(
                hit.getId(),
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                LocalDateTime.parse(hit.getTimestamp().format(Constant.FORMATTER), Constant.FORMATTER)
        );
    }
}
