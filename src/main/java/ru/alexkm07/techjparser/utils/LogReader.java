package ru.alexkm07.techjparser.utils;

import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.constants.Events;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Data
public class LogReader {

    private Path logPath;
    private List<Map<String, String>> logRows;
    private InfoForSearsh infoForSearsh;

    public LogReader(Path logPath) {
        this.logPath = logPath;
    }

    public LogReader() {
        this.logRows = new ArrayList<>();
    }

    public boolean readLogFile() {
        logRows = new ArrayList<>();
        try {

           // Long startReadFile = System.currentTimeMillis();

            List<String> fileStrings = Files.readAllLines(logPath);
        //    Long endReadFile = System.currentTimeMillis();
        //    System.out.println("time to read file: "+ (endReadFile-startReadFile));

            StringBuilder sb = new StringBuilder();

          //  Long startMakeString = System.currentTimeMillis();

            for (String line : fileStrings) {
                sb.append(line);
            }

           List<String> rowEvents = preparedString(sb.toString());

//            Long endMakeString = System.currentTimeMillis();
//            System.out.println("time to make string array: "+ (endMakeString-startMakeString));

            for (String row : rowEvents) {
                if (row.length() < 10) continue;
                Map<String, String> logRow = rowToLogRowConvert(row);
                if (logRow != null) logRows.add(logRow);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private List<String> preparedString(String stringToPrepare) {

        List<String> rows = new ArrayList<>();

        Pattern pattern = Pattern.compile("[0-9][0-9]:[0-9][0-9]\\.[0-9]+", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(stringToPrepare);
        String result = stringToPrepare;
        String prevResult = "";
        while (matcher.find()) {

            String resultGroup = matcher.group();

            if (resultGroup.length() < 10) continue;
            if(prevResult.isEmpty()) {
                prevResult = resultGroup;
                continue;
            }
            int fIndex = stringToPrepare.indexOf(prevResult);
            int lIndex = stringToPrepare.indexOf(resultGroup);

            String row = stringToPrepare.substring(fIndex,lIndex);
            prevResult = resultGroup;
            rows.add(row);
            //stringToPrepare = stringToPrepare.replace(row,"");
        }
        return rows;
    }

    private Map<String, String> rowToLogRowConvert(String row) {

        String[] strings = row.split(",");
        if (strings.length < 5) return null;

        String eventName = eventName(strings[1]);
        strings = null;

        Map<String, String> rowEvent = new HashMap<>();

        switch (eventName) {

            case (Events.dbmssqlEvent): {
                return convertToDbmssql(rowEvent, row);
            }
            case (Events.contextEvent): {
                return convertToContext(rowEvent, row);
            }
            case (Events.timeoutEvent): {
                return convertToTimeOut(rowEvent, row);
            }
            case (Events.tlockEvent): {
                return convertToTlock(rowEvent, row);
            }
            case (Events.excpEvent):{
                return convertExcpEvent(rowEvent, row);
            }

        }

        return null;
    }

    private Map<String, String> convertExcpEvent(Map<String, String> rowEvent, String row) {
        List<String> fieldList = infoForSearsh.getEventFieldMap().get(Events.excpEvent);

        rowEvent = convertRowToMapFields(fieldList, row,rowEvent);

        return rowEvent;
    }

    private Map<String, String> convertToTlock(Map<String, String> rowEvent, String row) {


        List<String> fieldList = infoForSearsh.getEventFieldMap().get(Events.tlockEvent);

        rowEvent = convertRowToMapFields(fieldList, row,rowEvent);

        return rowEvent;

    }

    private Map<String, String> convertToTimeOut(Map<String, String> rowEvent, String row) {

        List<String> fieldList = infoForSearsh.getEventFieldMap().get(Events.timeoutEvent);

        rowEvent = convertRowToMapFields(fieldList, row,rowEvent);

        return rowEvent;

    }

    private Map<String, String> convertToContext(Map<String, String> rowEvent, String row) {

        List<String> fieldList = infoForSearsh.getEventFieldMap().get(Events.contextEvent);

        rowEvent = convertRowToMapFields(fieldList, row,rowEvent);

        return rowEvent;
    }

    private Map<String, String> convertToDbmssql(Map<String, String> rowEvent, String row) {

        List<String> fieldList = infoForSearsh.getEventFieldMap().get(Events.dbmssqlEvent);

        rowEvent = convertRowToMapFields(fieldList, row,rowEvent);

        return rowEvent;
    }

    private Map<String, String> convertRowToMapFields(List<String> fieldList, String row, Map<String,String> rowEvent) {

        List<String> longFields = infoForSearsh.getLongFields();
        List<String> individualFields = infoForSearsh.getIndividualReadingFields();
        List<String> allFieldList = new ArrayList<>(fieldList);

      // Long startParsLongRows = System.nanoTime();
        for (String longField : longFields) {

            if (!allFieldList.contains(longField)) {
                continue;
            }
          String result = stringByPattern(row, infoForSearsh.getPatternMap().get(longField));
          //String result = stringBySplit(row,longField,"',");
            if(result != null){
                result = result.replace(longField+"=","");
                rowEvent.put(longField,result);
                row = row.replace(result,"");
            }

            allFieldList.remove(longField);
        }
      //Long endParsLongRows = System.nanoTime();
      //System.out.println("time to pars long " + (endParsLongRows-startParsLongRows));

        //Long startParsingIndividualFields = System.nanoTime();
        for (String individualField:individualFields){

            if (!allFieldList.contains(individualField)) {
                continue;
            }
            String[] strings = row.split(",");
            if(strings.length < 3){
                return null;
            }

            switch (individualField){

                case Events.dateTime : {
                    rowEvent.put(Events.dateTime,dateStartEvent(strings[0]));
                    break;
                }
                case (Events.duration) :{
                    rowEvent.put(Events.duration,duration(strings[0]));
                    break;
                }
                case (Events.eventName) :{
                    rowEvent.put(Events.eventName,eventName(strings[1]));
                    break;
                }

            }
            allFieldList.remove(individualField);

        }
//        Long endParsingIndividualFields = System.nanoTime();
//        System.out.println("time to pars Individual Fields " + (endParsingIndividualFields-startParsingIndividualFields));
//
        Long startParsingAllField = System.nanoTime();
        for (String field:allFieldList){


            Pattern pattern = infoForSearsh.getPatternMap().get(field);
            if(pattern==null){
                System.out.println("Не задан паттерн для поля " + field);
                continue;
            }
         String result = stringByPattern(row,pattern);
         //   String result = stringBySplit(row,field+"=",",");
            if(result == null){
                rowEvent.put(field,Events.na);
                continue;
            }
            result = result.replace(field+"=","");
            rowEvent.put(field,result);
        }
        Long endParsingAllField = System.nanoTime();
        //System.out.println("time to pars All Fields " + (endParsingAllField-startParsingAllField));
        return rowEvent;
    }

    private String stringByPattern(String row, Pattern pattern) {

        String result = Events.na;
        Matcher matcher = pattern.matcher(row);

        while (matcher.find()) {
            result = matcher.group();
            break;
        }

        return result;

    }

    private String stringBySplit(String row, String field, String endSplit){

        String[] strings = row.split(field);
        if (strings.length < 2) return Events.na;
        strings = strings[1].split(endSplit);

        if (strings.length < 1) return Events.na;

        return strings[0];

    }

    private String duration(String field) {
        String[] dataField = field.split("-");
        if (dataField.length != 2) return Events.na;
        return dataField[1];
    }

    private String eventName(String string) {
        String result = Events.na;
        if (Events.eventNames.contains(string)) result = string;
        return string;
    }

    private String dateStartEvent(String string) {
        String[] fileName = logPath.getFileName().toString().split("\\.");
        String name = fileName[0];
        String[] times = string.split("-");
        return name + times[0];
    }

}
