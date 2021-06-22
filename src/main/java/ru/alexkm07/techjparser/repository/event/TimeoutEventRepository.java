package ru.alexkm07.techjparser.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexkm07.techjparser.model.event.TimeoutEvent;
import ru.alexkm07.techjparser.model.fildClass.ProcessName;

import java.time.LocalDateTime;

public interface TimeoutEventRepository extends JpaRepository<TimeoutEvent, Long> {

    TimeoutEvent findByDateEventAndDurationAndProcessNameAndClientID(LocalDateTime date, Long duration, ProcessName processName, String clientId);

}
