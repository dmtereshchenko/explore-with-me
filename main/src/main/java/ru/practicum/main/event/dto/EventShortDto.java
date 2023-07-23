package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Constant;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    private Long id;
    private CategoryDto category;
    private UserShortDto initiator;
    private long confirmedRequests;
    private long views;
    private boolean paid;
    private String annotation;
    private String title;
    @JsonFormat(pattern = Constant.DTFormat)
    private LocalDateTime eventDate;

    public EventShortDto(Long id, CategoryDto category, UserShortDto initiator, boolean paid, String annotation, String title, LocalDateTime eventDate) {
        this.id = id;
        this.category = category;
        this.initiator = initiator;
        this.paid = paid;
        this.annotation = annotation;
        this.title = title;
        this.eventDate = eventDate;
    }
}
