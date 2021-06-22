package ru.alexkm07.techjparser.utils;

import ru.alexkm07.techjparser.constants.Events;
import ru.alexkm07.techjparser.model.event.ContextOneC;
import ru.alexkm07.techjparser.model.event.MssqlEvent;
import ru.alexkm07.techjparser.model.event.TimeoutEvent;
import ru.alexkm07.techjparser.model.event.TlockEvent;
import ru.alexkm07.techjparser.model.fildClass.*;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class LogToEventConverter {

    private final ApplicationNameService applicationNameService;
    private final ComputerNameService computerNameService;
    private final ProcessNameService processNameService;
    private final SqlService sqlService;
    private final ContextOneCService contextOneCService;
    private final ComputerOneCService computerOneCService;
    private final UserEventService userEventService;
    private final MSSQLEventService mssqlEventService;
    private final TimeOutEventService timeOutEventService;
    private final TlockService tlockService;
    private final RegionsService regionsService;

    public LogToEventConverter(ApplicationNameService applicationNameService, ComputerNameService computerNameService,
                               ProcessNameService processNameService, SqlService sqlService,
                               ContextOneCService contextOneCService, ComputerOneCService computerOneCService,
                               UserEventService userEventService, MSSQLEventService mssqlEventService, TimeOutEventService timeOutEventService, TlockService tlockService, RegionsService regionsService) {
        this.applicationNameService = applicationNameService;
        this.computerNameService = computerNameService;
        this.processNameService = processNameService;
        this.sqlService = sqlService;
        this.contextOneCService = contextOneCService;
        this.computerOneCService = computerOneCService;
        this.userEventService = userEventService;
        this.mssqlEventService = mssqlEventService;
        this.timeOutEventService = timeOutEventService;
        this.tlockService = tlockService;
        this.regionsService = regionsService;
    }


    public Object rowToEvent(Map<String, String> row) {

        Object event = null;

        String eventName = row.get("eventName");
        if (eventName == null) return null;

        switch (eventName) {

            case (Events.dbmssqlEvent): {
                event = (MssqlEvent) rowToMssqlEvent(row);
                break;
            }
            case (Events.contextEvent): {
                event = (ContextOneC) rowToContextEvent(row);
                break;
            }
            case (Events.timeoutEvent): {
                event = (TimeoutEvent) rowToTimeOut(row);
                break;
            }
            case (Events.tlockEvent): {
                event = (TlockEvent) rowToTlock(row);
                break;
            }

        }

        return event;
    }

    private Object rowToContextEvent(Map<String, String> row) {

        ContextOneC contextOneC = new ContextOneC();
        contextOneC.setDateEvent(dateEvent(row.get("dateEvent")));
        contextOneC.setDuration(Long.parseLong(row.get("duration")));
        contextOneC.setProcessName(processNameObject(row.get("processName")));
        contextOneC.setClientID(row.get("clientID"));
        contextOneC.setApplicationName(applicationNameObject(row.get("applicationName")));
        contextOneC.setComputerName(computerNameObject(row.get("computerName")));
        contextOneC.setConnectID(row.get("connectID").equals(Events.na) ? null : Long.parseLong(row.get("connectID")));
        contextOneC.setSessionID(row.get("sessionID").equals(Events.na) ? null : Long.parseLong(row.get("sessionID")));
        contextOneC.setUser(userEventObject(row.get("usr")));
        contextOneC.setDbPid(row.get("dbPid") == null || row.get("dbPid").equals(Events.na) ? null : Long.parseLong(row.get("dbPid")));
        contextOneC.setText(row.get("text"));
        contextOneC.setHash(row.get("text").hashCode());

        ContextOneC fromDb = contextOneCService.findByDateEventAndHashAndDuration(contextOneC.getDateEvent(),
                contextOneC.getHash(), contextOneC.getDuration());
        if (fromDb != null) return fromDb;

        return contextOneCService.save(contextOneC);
    }

    private Object rowToTimeOut(Map<String, String> row) {

        TimeoutEvent timeoutEvent = new TimeoutEvent();
        timeoutEvent.setDateEvent(dateEvent(row.get("dateEvent")));
        timeoutEvent.setDuration(Long.parseLong(row.get("duration")));
        timeoutEvent.setProcessName(processNameObject(row.get("processName")));
        timeoutEvent.setClientID(row.get("clientID"));
        timeoutEvent.setApplicationName(applicationNameObject(row.get("applicationName")));
        timeoutEvent.setComputerName(computerNameObject(row.get("computerName")));
        timeoutEvent.setConnectID(row.get("connectID").equals(Events.na) ? null : Long.parseLong(row.get("connectID")));
        timeoutEvent.setSessionID(row.get("sessionID").equals(Events.na) ? null : Long.parseLong(row.get("sessionID")));
        timeoutEvent.setUser(userEventObject(row.get("usr")));
        timeoutEvent.setContextText(row.get("text"));
        timeoutEvent.setHash(row.get("text").hashCode());
        timeoutEvent.setWaitConnections(row.get("waitConnections").equals(Events.na) ? null : Long.parseLong(row.get("waitConnections")));

        TimeoutEvent fromDb = timeOutEventService.findByDateEventAndDurationAndProcessNameAndClientID(timeoutEvent.getDateEvent(),
                timeoutEvent.getDuration(), timeoutEvent.getProcessName(), timeoutEvent.getClientID());
        if (fromDb != null) return fromDb;

        return timeOutEventService.save(timeoutEvent);
    }

    private Object rowToTlock(Map<String, String> row) {

        TlockEvent tlockEvent = new TlockEvent();
        tlockEvent.setDateEvent(dateEvent(row.get("dateEvent")));
        tlockEvent.setDuration(Long.parseLong(row.get("duration")));
        tlockEvent.setProcessName(processNameObject(row.get("processName")));
        tlockEvent.setClientID(row.get("clientID"));
        tlockEvent.setApplicationName(applicationNameObject(row.get("applicationName")));
        tlockEvent.setComputerName(computerNameObject(row.get("computerName")));
        tlockEvent.setConnectID(row.get("connectID").equals(Events.na) ? null : Long.parseLong(row.get("connectID")));
        tlockEvent.setSessionID(row.get("sessionID").equals(Events.na) ? null : Long.parseLong(row.get("sessionID")));
        tlockEvent.setUser(userEventObject(row.get("usr")));
        tlockEvent.setContextText(row.get("text"));
        tlockEvent.setHash(row.get("text").hashCode());
        tlockEvent.setRegions(regionsObjet(row.get("regions")));
        tlockEvent.setLocks(row.get("locks"));
        tlockEvent.setWaitConnections(row.get("waitConnections").equals(Events.na) ? null : Long.parseLong(row.get("waitConnections")));
        TlockEvent fromDb = tlockService.findByDateEventAndDurationAndProcessNameAndClientID(tlockEvent.getDateEvent(),
                tlockEvent.getDuration(), tlockEvent.getProcessName(), tlockEvent.getClientID());
        if (fromDb != null) return fromDb;

        return tlockService.save(tlockEvent);
    }


    private Object rowToMssqlEvent(Map<String, String> row) {

        MssqlEvent mssqlEvent = new MssqlEvent();
        mssqlEvent.setDateEvent(dateEvent(row.get("dateEvent")));
        mssqlEvent.setDuration(Long.parseLong(row.get("duration")));
        mssqlEvent.setProcessName(processNameObject(row.get("processName")));
        mssqlEvent.setClientID(row.get("clientID"));
        mssqlEvent.setApplicationName(applicationNameObject(row.get("applicationName")));
        mssqlEvent.setComputerName(computerNameObject(row.get("computerName")));
        mssqlEvent.setConnectID(row.get("connectID").equals(Events.na) ? null : Long.parseLong(row.get("connectID")));
        mssqlEvent.setSessionID(row.get("sessionID").equals(Events.na) ? null : Long.parseLong(row.get("sessionID")));
        mssqlEvent.setUser(userEventObject(row.get("usr")));
        mssqlEvent.setAppId(row.get("appID"));
        mssqlEvent.setTransId(row.get("trans").equals(Events.na) ? null : Long.parseLong(row.get("trans")));
        mssqlEvent.setDbPid(row.get("dbPid") == null || row.get("dbPid").equals(Events.na) ? null : Long.parseLong(row.get("dbPid")));
        mssqlEvent.setSql(sqlObject(row.get("sqlQueryText")));
        mssqlEvent.setRows(row.get("rows").equals(Events.na) ? null : Long.parseLong(row.get("rows")));
        mssqlEvent.setRowsAffected(row.get("rowsAffected").equals(Events.na) ? null : Long.parseLong(row.get("rowsAffected")));
        mssqlEvent.setContextText(row.get("context"));
        MssqlEvent fromDb = mssqlEventService.findByDateAndDurationAndProcessNameAndSql(mssqlEvent.getDateEvent()
                , mssqlEvent.getDuration(), mssqlEvent.getProcessName(), mssqlEvent.getSql());

        if (fromDb != null) return fromDb;
        return mssqlEventService.save(mssqlEvent);

    }

    private Sql sqlObject(String field) {
        if (field.equals(Events.na)) return null;

        Sql sql = sqlService.findByHashAndAndQuery(field.trim().hashCode(), field.trim());
        if (sql != null) return sql;

        sql = new Sql();
        sql.setHash(field.trim().hashCode());
        sql.setQuery(field.trim());

        return sqlService.save(sql);

    }

    private UserEvent userEventObject(String field) {
        if (field.equals(Events.na)) return null;

        UserEvent userEvent = userEventService.findByName(field.trim());
        if (userEvent != null) return userEvent;

        userEvent = new UserEvent();
        userEvent.setName(field.trim());

        return userEventService.save(userEvent);
    }

    private ComputerName computerNameObject(String field) {
        if (field.equals(Events.na)) return null;

        ComputerName computerName = computerNameService.findByName(field.trim());
        if (computerName != null) return computerName;

        computerName = new ComputerName();
        computerName.setName(field.trim());

        return computerNameService.save(computerName);
    }

    private ApplicationName applicationNameObject(String field) {

        if (field.equals(Events.na)) return null;

        ApplicationName applicationName = applicationNameService.findByName(field.trim());
        if (applicationName != null) return applicationName;

        applicationName = new ApplicationName();
        applicationName.setName(field.trim());

        return applicationNameService.save(applicationName);
    }

    private ProcessName processNameObject(String field) {

        if (field.equals(Events.na)) return null;

        ProcessName processName = processNameService.findByName(field.trim());
        if (processName != null) return processName;

        processName = new ProcessName();
        processName.setName(field.trim());


        return processNameService.save(processName);
    }

    private Regions regionsObjet(String field) {

        if (field.equals(Events.na)) return null;

        Regions regions = regionsService.findByText(field.trim());
        if (regions != null) return regions;

        regions = new Regions();
        regions.setText(field.trim());

        return regionsService.save(regions);

    }

    private LocalDateTime dateEvent(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm:ss.SSSSSS");
        return LocalDateTime.parse(dateString, formatter);
    }

}
