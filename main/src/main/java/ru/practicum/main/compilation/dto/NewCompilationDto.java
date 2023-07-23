package ru.practicum.main.compilation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
    private boolean pinned;
    @Nullable
    private List<Long> events;
}
