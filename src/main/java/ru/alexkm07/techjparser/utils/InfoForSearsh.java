package ru.alexkm07.techjparser.utils;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.constants.Events;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.regex.Pattern;



@Data
public class InfoForSearsh {

    private Map<String, Pattern> patternMap = new HashMap<>();

    private Map<String, List<String>> eventFieldMap = new HashMap<>();

    private List<String> longFields = new ArrayList<>();

    private List<String> individualReadingFields = new ArrayList<>();

    private List<String> ignoreExeption = new ArrayList<>();


    public InfoForSearsh() {
        initPatternMap();
        initFieldMap();
        initLongFields();
        initIndividualReadingFields();
        initIgnoreExeption();
    }

    private void initIgnoreExeption() {
        ignoreExeption.add("An operation was attempted on something that is not a socket");
        ignoreExeption.add("recv returns zero, disconnected line");
        ignoreExeption.add("An established connection was aborted by the software");
        ignoreExeption.add("system cannot find the file specified.");
        ignoreExeption.add(Events.na);
        ignoreExeption.add("existing connection was forcibly closed by the remote host");
        ignoreExeption.add("Неожиданный вызов метода");
        ignoreExeption.add("Процесс завершается. Исходящий вызов запрещен");
        ignoreExeption.add("Не найдено ни одного сервера с размещенным");
        ignoreExeption.add("onFinishConnection");
        ignoreExeption.add("OnFinishRpHost");
        ignoreExeption.add("elect returns zero");
        ignoreExeption.add("Рабочий процесс не найден");
        ignoreExeption.add("Обновление индекса уже запущено другим пользователем");
        ignoreExeption.add("Требуется переустановка соединения");
        ignoreExeption.add("Сеанс отсутствует или удаленID");
    }

    private void initPatternMap() {
        patternMap.put(Events.process, Pattern.compile(Events.process + "=[\\w-]*"));
        patternMap.put(Events.processName, Pattern.compile(Events.processName + "=[\\w-]*"));
        patternMap.put(Events.clientID, Pattern.compile(Events.clientID + "=[\\w-]*"));
        patternMap.put(Events.applicationName, Pattern.compile(Events.applicationName + "=[\\w-]*"));
        patternMap.put(Events.computerName, Pattern.compile(Events.computerName + "=[\\w-]*"));
        patternMap.put(Events.exception, Pattern.compile(Events.exception + "=[\\w-]*"));
        patternMap.put(Events.connectID, Pattern.compile(Events.connectID + "=[\\w-]*"));
        patternMap.put(Events.sessionID, Pattern.compile(Events.sessionID + "=[\\w-]*"));
        patternMap.put(Events.usr, Pattern.compile(Events.usr + "=[\\w-]*"));
        patternMap.put(Events.trans, Pattern.compile(Events.trans + "=[\\w-]*"));
        patternMap.put(Events.dbpid, Pattern.compile(Events.dbpid + "=[\\w-]*"));
        patternMap.put(Events.rows, Pattern.compile(Events.rows + "=[\\w-]*"));
        patternMap.put(Events.rowsAffected, Pattern.compile(Events.rowsAffected + "=[\\w-]*"));
        patternMap.put(Events.regions, Pattern.compile(Events.regions + "=[\\w-]*"));
        patternMap.put(Events.waitConnections, Pattern.compile(Events.waitConnections + "=[\\w-]*"));
        patternMap.put(Events.appID, Pattern.compile(Events.appID + "=[\\w-]*"));
        patternMap.put(Events.exceptClientID,Pattern.compile(Events.appID + "=[\\w-]*"));

        patternMap.put(Events.descr, Pattern.compile(Events.descr + "='([^']*)'"));
        patternMap.put(Events.sql, Pattern.compile(Events.sql + "='([^']*)'"));
        patternMap.put(Events.context, Pattern.compile(Events.context + "='([^']*)'"));
        patternMap.put(Events.locks, Pattern.compile(Events.locks + "='([^']*)'"));

    }

    private void initFieldMap() {

        eventFieldMap.put(Events.dbmssqlEvent, dbmssqlEventFields());
        eventFieldMap.put(Events.tlockEvent, tlockEventFields());
        eventFieldMap.put(Events.timeoutEvent, timeoutEventFields());
        eventFieldMap.put(Events.contextEvent, contextEventFields());
        eventFieldMap.put(Events.excpEvent, excpEventFields());
        eventFieldMap.put(Events.excpcntxEvent, excpcntxEventFields());
    }

    private List<String> excpcntxEventFields() {
        List<String> fields = Arrays.asList(
                Events.dateTime
                , Events.eventName
                , Events.clientComputerName
                , Events.serverComputerName
                , Events.userName
                , Events.connectString
        );

        return fields;

    }

    private List<String> excpEventFields() {
        List<String> fields = Arrays.asList(
                Events.dateTime
                , Events.eventName
                , Events.processName
                , Events.clientID
                , Events.exception
                , Events.applicationName
                , Events.computerName
                , Events.connectID
                , Events.descr
        );

        return fields;
        
    }

    private List<String> contextEventFields() {
        List<String> fields = Arrays.asList(
                Events.dateTime
                , Events.eventName
                , Events.duration
                , Events.process
                , Events.processName
                , Events.clientID
                , Events.applicationName
                , Events.computerName
                , Events.connectID
                , Events.sessionID
                , Events.usr
                , Events.appID
                , Events.context
        );

        return fields;

    }

    private List<String> dbmssqlEventFields() {

        List<String> fields = Arrays.asList(
                Events.dateTime
                , Events.eventName
                , Events.duration
                , Events.process
                , Events.processName
                , Events.clientID
                , Events.applicationName
                , Events.computerName
                , Events.connectID
                , Events.sessionID
                , Events.usr
                , Events.trans
                , Events.dbpid
                , Events.sql
                , Events.rows
                , Events.rowsAffected
                , Events.context
        );

        return fields;

    }

    private List<String> tlockEventFields() {
        List<String> fields = Arrays.asList(
                Events.dateTime
                , Events.duration
                , Events.eventName
                , Events.process
                , Events.processName
                , Events.clientID
                , Events.applicationName
                , Events.computerName
                , Events.connectID
                , Events.sessionID
                , Events.usr
                , Events.regions
                , Events.waitConnections
                , Events.context
                , Events.locks
        );

        return fields;
    }

    private List<String> timeoutEventFields() {
        List<String> fields = Arrays.asList(
                Events.dateTime
                , Events.duration
                , Events.eventName
                , Events.process
                , Events.processName
                , Events.clientID
                , Events.applicationName
                , Events.computerName
                , Events.connectID
                , Events.sessionID
                , Events.usr
                , Events.waitConnections
                , Events.context
        );

        return fields;
    }

    private void initLongFields() {
        longFields = Arrays.asList(
                Events.context
                ,Events.regions
                ,Events.descr
                ,Events.sql
                ,Events.locks
        );
    }

    private void initIndividualReadingFields() {
        individualReadingFields = Arrays.asList(
                Events.dateTime,
                Events.eventName,
                Events.duration
        );

    }



}
