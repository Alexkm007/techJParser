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

    public Sql findByHashAndMd5hash(Integer hash, String md5Hash){
        return sqlRepository.findByHashAndMd5hash(hash,md5Hash);
    }

    public Sql save(Sql sql){
        return sqlRepository.save(sql);
    }

}
