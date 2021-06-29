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

    public MssqlEvent findByDateAndDuration(LocalDateTime date, Long duration){
        return mssqlEventRepository.findByDateEventAndDuration(date, duration);
    }

    public MssqlEvent save(MssqlEvent mssqlEvent){
        return  mssqlEventRepository.save(mssqlEvent);
    }

}
