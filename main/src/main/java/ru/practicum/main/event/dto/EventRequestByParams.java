package ru.practicum.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.Constant;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestByParams {

    private List<Long> users;
    private List<Constant.State> states;
    private List<Long> categories;
    private String text;
    private Boolean paid;
    private Boolean onlyAvailable;
    private Constant.UserRequestSort sort;
    @DateTimeFormat(pattern = Constant.DTFormat)
    private LocalDateTime rangeStart;
    @DateTimeFormat(pattern = Constant.DTFormat)
    private LocalDateTime rangeEnd;
    @PositiveOrZero
    private int from = 0;
    @Positive
    private int size = 10;
}
