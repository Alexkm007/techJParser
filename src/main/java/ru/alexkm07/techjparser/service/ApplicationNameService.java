package ru.alexkm07.techjparser.service;

import ru.alexkm07.techjparser.model.fildClass.ApplicationName;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.repository.ApplicationNameRepository;


@Service
public class ApplicationNameService {

    private final ApplicationNameRepository applicationNameRepository;

    public ApplicationNameService(ApplicationNameRepository applicationNameRepository) {
        this.applicationNameRepository = applicationNameRepository;
    }

    public ApplicationName findByName(String s){
        return applicationNameRepository.findByName(s);
    }

    public ApplicationName save(ApplicationName applicationName){
        return applicationNameRepository.saveAndFlush(applicationName);
    }

}
