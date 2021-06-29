package ru.alexkm07.techjparser.service;

import ru.alexkm07.techjparser.model.fildClass.ProcessName;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.repository.ProcessNameRepository;

@Service
public class ProcessNameService {
    private final ProcessNameRepository processNameRepository;

    public ProcessNameService(ProcessNameRepository processNameRepository) {
        this.processNameRepository = processNameRepository;
    }

    public ProcessName findByName(String s){
        return processNameRepository.findByProc(s);
    }

    public ProcessName save(ProcessName processName){
        return processNameRepository.save(processName);
    }

}
