package ru.alexkm07.techjparser.repository;

import ru.alexkm07.techjparser.model.fildClass.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEventRepository extends JpaRepository<UserEvent,Long> {

    UserEvent findByName(String name);

}
