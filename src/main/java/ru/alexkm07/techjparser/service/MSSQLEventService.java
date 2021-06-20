package ru.alexkm07.techjparser.service;

import ru.alexkm07.techjparser.model.event.MssqlEvent;
import ru.alexkm07.techjparser.model.fildClass.ProcessName;
import ru.alexkm07.techjparser.model.fildClass.Sql;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.repository.event.MssqlEventRepository;


import java.time.LocalDateTime;

@Service
public class MSSQLEventService {

    private final MssqlEventRepository mssqlEventRepository;

    public MSSQLEventService(MssqlEventRepository mssqlEventRepository) {
        this.mssqlEventRepository = mssqlEventRepository;
    }

    public MssqlEvent findByDate(LocalDateTime localDateTime){
        return mssqlEventRepository.findByDateEvent(localDateTime);
    }

    public MssqlEvent findByDateAndDurationAndProcessNameAndSql(LocalDateTime date, Long duration, ProcessName processName, Sql sql){
        return mssqlEventRepository.findByDateAndDurationAndProcessNameAndSql(date, duration,processName,sql);
    }

    public MssqlEvent save(MssqlEvent mssqlEvent){
        return  mssqlEventRepository.saveAndFlush(mssqlEvent);
    }

}
