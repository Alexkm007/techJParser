package service;

import model.fildClass.ContextOneC;
import model.fildClass.Sql;
import org.springframework.stereotype.Service;
import repository.ContextOneCRepository;

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
