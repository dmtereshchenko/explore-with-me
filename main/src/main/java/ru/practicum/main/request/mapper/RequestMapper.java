package ru.practicum.main.request.mapper;

import ru.practicum.Constant;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.model.Request;

import java.time.LocalDateTime;

public class RequestMapper {

    public static ParticipationRequestDto toDto(Request request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus(),
                LocalDateTime.parse(request.getCreated().format(Constant.FORMATTER), Constant.FORMATTER)
        );
    }
}
