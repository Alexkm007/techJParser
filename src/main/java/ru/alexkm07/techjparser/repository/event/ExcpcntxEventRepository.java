package ru.alexkm07.techjparser.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexkm07.techjparser.model.event.ExcpEvent;
import ru.alexkm07.techjparser.model.event.ExcpсntxEvent;

import java.time.LocalDateTime;

public interface ExcpcntxEventRepository extends JpaRepository<ExcpсntxEvent, Long> {

    ExcpсntxEvent findByDate(LocalDateTime dateTime);

}
