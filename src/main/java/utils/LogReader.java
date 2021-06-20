package utils;

import constants.Events;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class LogReader {

    private Path logPath;
    private List<Map<String, String>> logRows;

    public LogReader(Path logPath) {
        this.logPath = logPath;
        this.logRows = new ArrayList<>();
    }

    public boolean readLogFile() {
        try {
            List<String> listRows = Files.readAllLines(logPath);

            for (String row : listRows) {
                Map<String, String> logRow = rowToLogRowConvert(row);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private Map<String, String> rowToLogRowConvert(String row) {

        String[] strings = row.split(",");
        String eventName = eventName(strings[1]);

        Map<String, String> rowEvent = new HashMap<>();

        switch (eventName){

            case (Events.dbmssqlEvent): {
                return convertToDbmssql(strings,rowEvent);
            }
            case (Events.contextEvent): {
                return convertToContext(strings,rowEvent);
            }

        }

        return null;
    }

    private Map<String, String> convertToContext(String[] strings,Map<String, String>  rowEvent) {

        rowEvent.put("dateStart", dateStartEvent(strings[0], logPath.getFileName()));
        rowEvent.put("eventName", Events.contextEvent);
        rowEvent.put("process", dateFromRowByName(strings,Events.process));
        rowEvent.put("processName", dateFromRowByName(strings,Events.processName));
        rowEvent.put("clientID", dateFromRowByName(strings,Events.clientID));
        rowEvent.put("applicationName", dateFromRowByName(strings,Events.applicationName));
        rowEvent.put("computerName", dateFromRowByName(strings,Events.computerName));
        rowEvent.put("connectID", dateFromRowByName(strings,Events.connectID));
        rowEvent.put("sessionID", dateFromRowByName(strings,Events.sessionID));
        rowEvent.put("usr", dateFromRowByName(strings,Events.usr));
        rowEvent.put("appID", dateFromRowByName(strings,Events.appID));
        rowEvent.put("context", dateFromRowByName(strings,"Context="));

        return rowEvent;
    }

    private Map<String, String> convertToDbmssql(String[] strings,Map<String, String>  rowEvent) {

        rowEvent.put("dateStart", dateStartEvent(strings[0], logPath.getFileName()));
        rowEvent.put("eventName", Events.dbmssqlEvent);
        rowEvent.put("process", dateFromRowByName(strings,Events.process));
        rowEvent.put("processName", dateFromRowByName(strings,Events.processName));
        rowEvent.put("clientID", dateFromRowByName(strings,Events.clientID));
        rowEvent.put("applicationName", dateFromRowByName(strings,Events.applicationName));
        rowEvent.put("computerName", dateFromRowByName(strings,Events.computerName));
        rowEvent.put("connectID", dateFromRowByName(strings,Events.connectID));
        rowEvent.put("sessionID", dateFromRowByName(strings,Events.sessionID));
        rowEvent.put("usr", dateFromRowByName(strings,Events.usr));
        rowEvent.put("appID", dateFromRowByName(strings,Events.appID));
        rowEvent.put("trans", dateFromRowByName(strings,Events.appID));
        rowEvent.put("dbpid",dateFromRowByName(strings,Events.dbpid));
        rowEvent.put("sqlQueryText", getQueryOrContext(strings,"Sql="));
        rowEvent.put("rows",dateFromRowByName(strings,Events.rows));
        rowEvent.put("RowsAffected",dateFromRowByName(strings,Events.rows));

        return rowEvent;
    }


    private String dateFromRowByName(String[] strings,String name) {
        String string = Events.na;

        for(String field:strings){
            String[] dataField =  field.split("=");
            if(dataField.length != 2) continue;

            if (dataField[0].contains(name)){
                string = dataField[1];
                break;
            }
        }

        return string;

    }

    private String eventName(String string) {
        String result = Events.na;
        if(Events.eventNames.contains(string)) result = string;
        return string;
    }

    private String dateStartEvent(String string, Path file) {
        String[] fileName = file.toString().split(".");
        String name = fileName[0];
        String[] times = string.split("-");
        return name + times[0];
    }

    private String getQueryOrContext(String[] strings,String search){

        String result = Events.na;

        for(String field:strings){
            if (field.contains(search)){
                result =  field.replace(search,"");
                break;
            }
        }
        return result;
    }

}
