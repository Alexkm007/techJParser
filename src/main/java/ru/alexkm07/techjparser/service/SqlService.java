package ru.alexkm07.techjparser.service;

import ru.alexkm07.techjparser.model.fildClass.Sql;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.repository.SqlRepository;


@Service
public class SqlService {
    private final SqlRepository sqlRepository;

    public SqlService(SqlRepository sqlRepository) {
        this.sqlRepository = sqlRepository;
    }

    public Sql findByHashAndAndQuery(Integer hash, String query){
        return sqlRepository.findByHashAndAndQuery(hash,query);
    }

    public Sql save(Sql sql){
        return sqlRepository.saveAndFlush(sql);
    }

}
