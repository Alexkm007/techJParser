package service;

import model.fildClass.Sql;
import org.springframework.stereotype.Service;
import repository.SqlRepository;

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
