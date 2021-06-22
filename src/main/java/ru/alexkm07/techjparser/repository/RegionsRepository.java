package ru.alexkm07.techjparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexkm07.techjparser.model.fildClass.ApplicationName;
import ru.alexkm07.techjparser.model.fildClass.Regions;

public interface RegionsRepository extends JpaRepository<Regions,Long> {

    Regions findByText(String name);

}
