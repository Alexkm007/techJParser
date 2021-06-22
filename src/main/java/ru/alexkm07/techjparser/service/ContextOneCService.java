package ru.alexkm07.techjparser.service;

import ru.alexkm07.techjparser.model.event.ContextOneC;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.repository.ContextOneCRepository;

import java.time.LocalDateTime;


@Service
public class ContextOneCService {

    private final ContextOneCRepository contextOneCRepository;

    public ContextOneCService(ContextOneCRepository contextOneCRepository) {
        this.contextOneCRepository = contextOneCRepository;
    }

    public ContextOneC findByDateEventAndHashAndDuration(LocalDateTime dateEvent, Integer hash, Long duration){
        return contextOneCRepository.findByDateEventAndHashAndDuration(dateEvent,hash,duration);
    }

    public ContextOneC findByHashAndAndText(Integer hash, String query){
        return contextOneCRepository.findByHashAndText(hash,query);
    }

    public ContextOneC save(ContextOneC contextOneC){
        return contextOneCRepository.saveAndFlush(contextOneC);
    }
}
