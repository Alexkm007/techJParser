package ru.alexkm07.techjparser.repository.event;

import ru.alexkm07.techjparser.model.event.MssqlEvent;
import ru.alexkm07.techjparser.model.fildClass.ProcessName;
import ru.alexkm07.techjparser.model.fildClass.Sql;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MssqlEventRepository extends JpaRepository<MssqlEvent,Long> {

    MssqlEvent findByDateEvent(LocalDateTime localDateTime);

    MssqlEvent findByDateEventAndDuration(LocalDateTime date, Long duration);

}
