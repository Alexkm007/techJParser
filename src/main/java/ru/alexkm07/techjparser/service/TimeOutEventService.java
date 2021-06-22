package ru.alexkm07.techjparser.service;

import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.model.event.MssqlEvent;
import ru.alexkm07.techjparser.model.event.TimeoutEvent;
import ru.alexkm07.techjparser.model.fildClass.ProcessName;
import ru.alexkm07.techjparser.model.fildClass.Sql;
import ru.alexkm07.techjparser.repository.event.MssqlEventRepository;
import ru.alexkm07.techjparser.repository.event.TimeoutEventRepository;

import java.time.LocalDateTime;

@Service
public class TimeOutEventService {

    private final TimeoutEventRepository timeoutEventRepository;

    public TimeOutEventService(TimeoutEventRepository timeoutEventRepository) {
        this.timeoutEventRepository = timeoutEventRepository;
    }


    public TimeoutEvent findByDateEventAndDurationAndProcessNameAndClientID(LocalDateTime date,
                                                                            Long duration, ProcessName processName, String clientId){
        return timeoutEventRepository.findByDateEventAndDurationAndProcessNameAndClientID(date, duration,processName,clientId);
    }

    public TimeoutEvent save(TimeoutEvent timeoutEvent){
        return  timeoutEventRepository.saveAndFlush(timeoutEvent);
    }

}
