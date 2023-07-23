package ru.practicum.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stats")
public class Hit {

    @Id
    @GeneratedValue
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
