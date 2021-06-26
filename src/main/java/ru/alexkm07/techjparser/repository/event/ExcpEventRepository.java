package ru.alexkm07.techjparser.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexkm07.techjparser.model.event.ExcpEvent;

import java.time.LocalDateTime;

public interface ExcpEventRepository extends JpaRepository<ExcpEvent, Long> {

    ExcpEvent findByDateEventAndExceptionAndAndHash(LocalDateTime dateTime, String exception, Integer hash);

}
