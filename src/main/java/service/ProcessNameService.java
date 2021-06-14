package service;

import model.fildClass.ProcessName;
import org.springframework.stereotype.Service;
import repository.ProcessNameRepository;
@Service
public class ProcessNameService {
    private final ProcessNameRepository processNameRepository;

    public ProcessNameService(ProcessNameRepository processNameRepository) {
        this.processNameRepository = processNameRepository;
    }

    public ProcessName findByName(String s){
        return processNameRepository.findByName(s);
    }

    public ProcessName save(ProcessName processName){
        return processNameRepository.saveAndFlush(processName);
    }

}
