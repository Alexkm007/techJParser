package ru.alexkm07.techjparser.service;

import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.model.event.TimeoutEvent;
import ru.alexkm07.techjparser.model.event.TlockEvent;
import ru.alexkm07.techjparser.model.fildClass.ProcessName;
import ru.alexkm07.techjparser.repository.event.TimeoutEventRepository;
import ru.alexkm07.techjparser.repository.event.TlockEventRepository;

import java.time.LocalDateTime;

@Service
public class TlockService {

    private final TlockEventRepository tlockEventRepository;

    public TlockService(TlockEventRepository tlockEventRepository) {
        this.tlockEventRepository = tlockEventRepository;
    }


    public TlockEvent findByDateEventAndDurationAndProcessNameAndClientID(LocalDateTime date,
                                                                          Long duration, ProcessName processName, String clientId){
        return tlockEventRepository.findByDateEventAndDurationAndProcessNameAndClientID(date, duration,processName,clientId);
    }

    public TlockEvent save(TlockEvent tlockEvent){
        return  tlockEventRepository.saveAndFlush(tlockEvent);
    }

}
