package ru.alexkm07.techjparser.service;

import ru.alexkm07.techjparser.model.fildClass.ContextOneC;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.repository.ContextOneCRepository;


@Service
public class ContextOneCService {

    private final ContextOneCRepository contextOneCRepository;

    public ContextOneCService(ContextOneCRepository contextOneCRepository) {
        this.contextOneCRepository = contextOneCRepository;
    }

    public ContextOneC findByHashAndAndText(Integer hash, String query){
        return contextOneCRepository.findByHashAndText(hash,query);
    }

    public ContextOneC save(ContextOneC contextOneC){
        return contextOneCRepository.saveAndFlush(contextOneC);
    }
}
