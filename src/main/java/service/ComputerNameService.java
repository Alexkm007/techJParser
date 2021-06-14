package service;

import model.fildClass.ComputerName;
import org.springframework.stereotype.Service;
import repository.ComputerNameRepository;
@Service
public class ComputerNameService {
    private final ComputerNameRepository computerNameRepository;

    public ComputerNameService(ComputerNameRepository computerNameRepository) {
        this.computerNameRepository = computerNameRepository;
    }

    public ComputerName findByName(String s){
        return computerNameRepository.findByName(s);
    }

    public ComputerName save(ComputerName computerName){
        return computerNameRepository.saveAndFlush(computerName);
    }

}
