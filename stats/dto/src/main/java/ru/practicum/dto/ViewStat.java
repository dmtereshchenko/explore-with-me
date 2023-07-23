package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ViewStat {

    @JsonProperty("app")
    private String app;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("hits")
    private Long hits;
}
