package ru.alexkm07.techjparser.service;

import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.model.fildClass.ApplicationName;
import ru.alexkm07.techjparser.model.fildClass.Regions;
import ru.alexkm07.techjparser.repository.ApplicationNameRepository;
import ru.alexkm07.techjparser.repository.RegionsRepository;


@Service
public class RegionsService {

    private final RegionsRepository regionsRepository;

    public RegionsService(RegionsRepository repository) {
        this.regionsRepository = repository;
    }

    public Regions findByText(String s){
        return regionsRepository.findByText(s);
    }

    public Regions save(Regions regions){
        return regionsRepository.save(regions);
    }

}
