package ru.alexkm07.techjparser.repository;

import ru.alexkm07.techjparser.model.fildClass.ComputerName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerNameRepository extends JpaRepository<ComputerName,Long> {
    ComputerName findByCompName(String name);
}
