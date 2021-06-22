package  ru.alexkm07.techjparser.utils;

import ru.alexkm07.techjparser.constants.Events;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            List<String> fileStrings = Files.readAllLines(logPath);

            StringBuilder sb = new StringBuilder();
            for (String line : fileStrings) {
                sb.append(line);
            }

            String rowData = preparedString(sb.toString());
            String[] rows  = rowData.split("###");

            for (String row : rows) {
                if(row.length()<10) continue;
                Map<String, String> logRow = rowToLogRowConvert(row);
                if (logRow != null) logRows.add(logRow);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    private String preparedString(String stringToPrepare){

        Pattern pattern = Pattern.compile("\\d\\d:\\d\\d\\.\\d{1,}-\\d{1,}",Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(stringToPrepare);
        String result = stringToPrepare;
        while(matcher.find()){
            if (matcher.group().length() < 10) continue;
            result = result.replace(matcher.group(),"###"+matcher.group());
        }
        return result;
    }


    private Map<String, String> rowToLogRowConvert(String row) {

        String[] strings = row.split(",");
        if(strings.length < 5) return null;

        String eventName = eventName(strings[1]);
        strings = null;

        Map<String, String> rowEvent = new HashMap<>();

        switch (eventName) {

            case (Events.dbmssqlEvent): {
                return convertToDbmssql(rowEvent,row);
            }
            case (Events.contextEvent): {
                return convertToContext(rowEvent,row);
            }
            case (Events.timeoutEvent):{
                return convertToTimeOut(rowEvent,row);
            }
            case (Events.tlockEvent):{
                return convertToTlock(rowEvent,row);
            }

        }

        return null;
    }

    private Map<String, String> convertToTlock(Map<String, String> rowEvent, String row) {

        String regions = getRegions(row);
        row = row.replace(regions,"");
        rowEvent.put("regions",regions.length() > 1000 ? regions.substring(0,999):regions);
        regions = null;

        rowEvent.put("text", getQueryOrContext(row, "Context="));
        row = row.replace(rowEvent.get("text"),"");

        rowEvent.put("locks", getLocks(row));
        row = row.replace(rowEvent.get("locks"),"");

        String[] strings = row.split(",");

        rowEvent.put("dateEvent", dateStartEvent(strings[0], logPath.getFileName()));
        rowEvent.put("duration", duration(strings[0]));
        rowEvent.put("eventName", Events.tlockEvent);
        rowEvent.put("process", dataFromRowByName(strings, Events.process));
        rowEvent.put("processName", dataFromRowByName(strings, Events.processName).toLowerCase());
        rowEvent.put("clientID", dataFromRowByName(strings, Events.clientID));
        rowEvent.put("applicationName", dataFromRowByName(strings, Events.applicationName));
        rowEvent.put("computerName", dataFromRowByName(strings, Events.computerName));
        rowEvent.put("connectID", dataFromRowByName(strings, Events.connectID));
        rowEvent.put("sessionID", dataFromRowByName(strings, Events.sessionID));
        rowEvent.put("usr", dataFromRowByName(strings, Events.usr));
        rowEvent.put("waitConnections", dataFromRowByName(strings, Events.waitConnections));

        return rowEvent;

    }


    private Map<String, String> convertToTimeOut(Map<String, String> rowEvent, String row) {

        rowEvent.put("text", getQueryOrContext(row, "Context="));
        row = row.replace(rowEvent.get("text"),"");
        String[] strings = row.split(",");

        rowEvent.put("dateEvent", dateStartEvent(strings[0], logPath.getFileName()));
        rowEvent.put("duration", duration(strings[0]));
        rowEvent.put("eventName", Events.timeoutEvent);
        rowEvent.put("process", dataFromRowByName(strings, Events.process));
        rowEvent.put("processName", dataFromRowByName(strings, Events.processName).toLowerCase());
        rowEvent.put("clientID", dataFromRowByName(strings, Events.clientID));
        rowEvent.put("applicationName", dataFromRowByName(strings, Events.applicationName));
        rowEvent.put("computerName", dataFromRowByName(strings, Events.computerName));
        rowEvent.put("connectID", dataFromRowByName(strings, Events.connectID));
        rowEvent.put("sessionID", dataFromRowByName(strings, Events.sessionID));
        rowEvent.put("usr", dataFromRowByName(strings, Events.usr));
        rowEvent.put("waitConnections", dataFromRowByName(strings, Events.waitConnections));


        return rowEvent;

    }

    private Map<String, String> convertToContext(Map<String, String> rowEvent,String row) {

        rowEvent.put("text", getQueryOrContext(row, "Context="));
        row = row.replace(rowEvent.get("text"),"");
        String[] strings = row.split(",");

        rowEvent.put("dateEvent", dateStartEvent(strings[0], logPath.getFileName()));
        rowEvent.put("duration", duration(strings[0]));
        rowEvent.put("eventName", Events.contextEvent);
        rowEvent.put("process", dataFromRowByName(strings, Events.process));
        rowEvent.put("processName", dataFromRowByName(strings, Events.processName).toLowerCase());
        rowEvent.put("clientID", dataFromRowByName(strings, Events.clientID));
        rowEvent.put("applicationName", dataFromRowByName(strings, Events.applicationName));
        rowEvent.put("computerName", dataFromRowByName(strings, Events.computerName));
        rowEvent.put("connectID", dataFromRowByName(strings, Events.connectID));
        rowEvent.put("sessionID", dataFromRowByName(strings, Events.sessionID));
        rowEvent.put("usr", dataFromRowByName(strings, Events.usr));
        rowEvent.put("appID", dataFromRowByName(strings, Events.appID));


        return rowEvent;
    }

    private Map<String, String> convertToDbmssql(Map<String, String> rowEvent,String row) {

        rowEvent.put("sqlQueryText", getQueryOrContext(row, "Sql="));
        row = row.replace(rowEvent.get("sqlQueryText"),"");
        rowEvent.put("context", getQueryOrContext(row, "Context="));
        row = row.replace(rowEvent.get("context"),"");
        String[] strings = row.split(",");

        rowEvent.put("dateEvent", dateStartEvent(strings[0], logPath.getFileName()));
        rowEvent.put("duration", duration(strings[0]));
        rowEvent.put("eventName", Events.dbmssqlEvent);
        rowEvent.put("process", dataFromRowByName(strings, Events.process));
        rowEvent.put("processName", dataFromRowByName(strings, Events.processName).toLowerCase());
        rowEvent.put("clientID", dataFromRowByName(strings, Events.clientID));
        rowEvent.put("applicationName", dataFromRowByName(strings, Events.applicationName));
        rowEvent.put("computerName", dataFromRowByName(strings, Events.computerName));
        rowEvent.put("connectID", dataFromRowByName(strings, Events.connectID));
        rowEvent.put("sessionID", dataFromRowByName(strings, Events.sessionID));
        rowEvent.put("usr", dataFromRowByName(strings, Events.usr));
        rowEvent.put("appID", dataFromRowByName(strings, Events.appID));
        rowEvent.put("trans", dataFromRowByName(strings, Events.trans));
        rowEvent.put("dbpid", dataFromRowByName(strings, Events.dbpid));
        rowEvent.put("rows", dataFromRowByName(strings, Events.rows));
        rowEvent.put("rowsAffected", dataFromRowByName(strings, Events.rows));


        return rowEvent;
    }

    private String duration(String field) {
        String[] dataField = field.split("-");
        if (dataField.length != 2) return Events.na;
        return dataField[1];
    }


    private String dataFromRowByName(String[] strings, String name) {
        String string = Events.na;

        for (String field : strings) {
            String[] dataField = field.split("=");
            if (dataField.length != 2) continue;

            if (dataField[0].contains(name)) {
                string = dataField[1];
                break;
            }
        }

        return string;
    }

    private String eventName(String string) {
        String result = Events.na;
        if (Events.eventNames.contains(string)) result = string;
        return string;
    }

    private String dateStartEvent(String string, Path file) {
        String[] fileName = file.toString().split("\\.");
        String name = fileName[0];
        String[] times = string.split("-");
        return name + times[0];
    }

    private String getQueryOrContext(String strings, String search) {

        String result = Events.na;
        Pattern pattern = Pattern.compile(search+"\'([^\']*)\'");


        Matcher matcher = pattern.matcher(strings);

        while(matcher.find()){
            result = matcher.group().replace(search,"");
        }

        return result;
    }

    private String getRegions(String row) {
        String result = Events.na;
        String[] strings = row.split("Regions=");
        if(strings.length<2) return result;
        strings = strings[1].split(",Locks=");
        if(strings.length<2) return result;

        return strings[0];
    }

    private String getLocks(String strings) {

        String result = Events.na;
        Pattern pattern = Pattern.compile("Locks=\'([^\']*)\'");


        Matcher matcher = pattern.matcher(strings);

        while(matcher.find()){
            result = matcher.group().replace("Locks=","");
        }

        return result.length() > 1000 ? result.substring(0,999):result;
    }


}
