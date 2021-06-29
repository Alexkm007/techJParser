package ru.alexkm07.techjparser.repository;

import ru.alexkm07.techjparser.model.fildClass.Sql;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlRepository extends JpaRepository<Sql,Long> {
    Sql findByHashAndMd5hash(Integer hash, String md5hash);
}
