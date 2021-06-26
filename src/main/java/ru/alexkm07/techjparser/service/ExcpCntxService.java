package ru.alexkm07.techjparser.service;

import ru.alexkm07.techjparser.model.event.ExcpсntxEvent;
import ru.alexkm07.techjparser.repository.event.ExcpcntxEventRepository;

import java.time.LocalDateTime;

public class ExcpCntxService {

    private final ExcpcntxEventRepository excpcntxEventRepository;

    public ExcpCntxService(ExcpcntxEventRepository excpcntxEventRepository) {
        this.excpcntxEventRepository = excpcntxEventRepository;
    }

    public ExcpсntxEvent save(ExcpсntxEvent event){
       return excpcntxEventRepository.saveAndFlush(event);
    }

    public ExcpсntxEvent findByDate(LocalDateTime dateTime, String exception, Integer Hash){
        return excpcntxEventRepository.findByDate(dateTime);
    }

}
