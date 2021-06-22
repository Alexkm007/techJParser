package ru.alexkm07.techjparser.repository;

import ru.alexkm07.techjparser.model.event.ContextOneC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ContextOneCRepository extends JpaRepository<ContextOneC,Long> {

    ContextOneC findByHashAndText(Integer hash, String text);

    ContextOneC findByDateEventAndHashAndDuration(LocalDateTime dateEvent, Integer hash, Long duration);
}
