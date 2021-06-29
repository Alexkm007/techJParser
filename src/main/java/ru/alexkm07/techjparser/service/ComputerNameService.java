package ru.alexkm07.techjparser.service;

import ru.alexkm07.techjparser.model.fildClass.ComputerName;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.repository.ComputerNameRepository;

@Service
public class ComputerNameService {
    private final ComputerNameRepository computerNameRepository;

    public ComputerNameService(ComputerNameRepository computerNameRepository) {
        this.computerNameRepository = computerNameRepository;
    }

    public ComputerName findByName(String s){
        return computerNameRepository.findByCompName(s);
    }

    public ComputerName save(ComputerName computerName){
        return computerNameRepository.save(computerName);
    }

}
