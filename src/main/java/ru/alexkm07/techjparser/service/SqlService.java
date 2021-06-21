package ru.alexkm07.techjparser.service;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import ru.alexkm07.techjparser.model.fildClass.Sql;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.repository.SqlRepository;

@Log4j2
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
        //log.log(Level.DEBUG,"SQL length query: " + sql.getQuery().length());
        return sqlRepository.saveAndFlush(sql);
    }

}
