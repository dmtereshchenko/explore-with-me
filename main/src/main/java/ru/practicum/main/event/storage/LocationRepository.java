package ru.practicum.main.event.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.event.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
