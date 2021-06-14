package service;

import model.event.MssqlEvent;
import org.springframework.stereotype.Service;
import repository.event.MssqlEventRepository;

import java.time.LocalDateTime;

@Service
public class MSSQLEventService {

    private final MssqlEventRepository mssqlEventRepository;

    public MSSQLEventService(MssqlEventRepository mssqlEventRepository) {
        this.mssqlEventRepository = mssqlEventRepository;
    }

    public MssqlEvent findByDate(LocalDateTime localDateTime){
        return mssqlEventRepository.findByDateEvent(localDateTime);
    }

    public MssqlEvent save(MssqlEvent mssqlEvent){
        return  mssqlEventRepository.saveAndFlush(mssqlEvent);
    }

}
