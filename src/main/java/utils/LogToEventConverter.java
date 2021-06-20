package utils;

import org.springframework.stereotype.Component;
import service.*;

import java.util.Map;

@Component
public class LogToEventConverter {

    private final ApplicationNameService applicationNameService;
    private final ComputerNameService computerNameService;
    private final ProcessNameService processNameService;
    private final SqlService sqlService;
    private final ContextOneCService contextOneCService;
    private final ComputerOneCService computerOneCService;


    public LogToEventConverter(ApplicationNameService applicationNameService, ComputerNameService computerNameService,
                               ProcessNameService processNameService, SqlService sqlService,
                               ContextOneCService contextOneCService, ComputerOneCService computerOneCService) {
        this.applicationNameService = applicationNameService;
        this.computerNameService = computerNameService;
        this.processNameService = processNameService;
        this.sqlService = sqlService;
        this.contextOneCService = contextOneCService;
        this.computerOneCService = computerOneCService;
    }


    public Object rowToEvent(Map<String,String> row){

        Object event = null;

        String eventName = row.get("eventName");
        if(eventName == null) return null;

        switch (eventName.toLowerCase()) {

            case ("mssql"):{
                event = rowToMssqlEvent(row);
             break;
            }

        }

        return event;
    }

    private Object rowToMssqlEvent(Map<String,String> row) {





        return null;
    }

}
