package ru.alexkm07.techjparser.service;

import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.model.event.ExcpEvent;
import ru.alexkm07.techjparser.repository.event.ExcpEventRepository;

import java.time.LocalDateTime;

@Service
public class ExcpService {

    private final ExcpEventRepository excpEventRepository;

    public ExcpService(ExcpEventRepository excpEventRepository) {
        this.excpEventRepository = excpEventRepository;
    }

    public ExcpEvent save( ExcpEvent excpEvent){
       return excpEventRepository.save(excpEvent);
    }

    public ExcpEvent findByDateEventAndAndHash(LocalDateTime dateTime,  Integer hash){
        return excpEventRepository.findByDateEventAndHash(dateTime,hash);
    }

}
