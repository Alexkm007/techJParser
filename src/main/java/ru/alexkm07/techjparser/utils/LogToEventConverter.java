package ru.alexkm07.techjparser.utils;

import org.apache.commons.codec.digest.DigestUtils;
import ru.alexkm07.techjparser.constants.Events;
import ru.alexkm07.techjparser.model.event.*;
import ru.alexkm07.techjparser.model.fildClass.*;
import org.springframework.stereotype.Service;
import ru.alexkm07.techjparser.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    private final ExcpService excpService;
    private final ExcpCntxService excpCntxService;

    public LogToEventConverter(ApplicationNameService applicationNameService, ComputerNameService computerNameService,
                               ProcessNameService processNameService, SqlService sqlService,
                               ContextOneCService contextOneCService, ComputerOneCService computerOneCService,
                               UserEventService userEventService, MSSQLEventService mssqlEventService, TimeOutEventService timeOutEventService, TlockService tlockService, RegionsService regionsService, ExcpService excpService, ExcpCntxService excpCntxService) {
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
        this.excpService = excpService;
        this.excpCntxService = excpCntxService;
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
            case (Events.excpEvent):{
                event = (ExcpEvent) rowToExcpEvent(row);
            }


        }

        return event;
    }

    private ExcpEvent rowToExcpEvent(Map<String, String> row) {
        ExcpEvent excpEvent = new ExcpEvent();
        InfoForSearsh infoForSearsh = new InfoForSearsh();
        List<String> filters = infoForSearsh.getIgnoreExeption();

        for (String filter:filters){

            if(row.get(Events.descr).contains(filter)){
                return null;
            }

        }

        excpEvent.setDateEvent(dateEvent(row.get(Events.dateTime)));
        excpEvent.setClientID(row.get(Events.clientID));
        excpEvent.setProcess(row.get(Events.process));
        excpEvent.setException(row.get(Events.exception));
        excpEvent.setDescr(row.get(Events.descr));
        excpEvent.setHash(row.get(Events.descr).hashCode());
        excpEvent.setApplicationName(applicationNameObject(row.get(Events.applicationName)));
        excpEvent.setProcessName(processNameObject(row.get(Events.processName)));
        excpEvent.setComputerName(computerNameObject(row.get(Events.computerName)));
        excpEvent.setConnectId(row.get(Events.connectID).equals(Events.na) ? null : Long.parseLong(row.get(Events.connectID)));

        ExcpEvent fromDb = excpService.findByDateEventAndAndHash(excpEvent.getDateEvent(),
                excpEvent.getHash());
        if (fromDb != null) return fromDb;

        return excpService.save(excpEvent);

    }

    private Object rowToContextEvent(Map<String, String> row) {

        ContextOneC contextOneC = new ContextOneC();
        contextOneC.setDateEvent(dateEvent(row.get(Events.dateTime)));
        contextOneC.setDuration(Long.parseLong(row.get(Events.duration)));
        contextOneC.setProcessName(processNameObject(row.get(Events.processName)));
        contextOneC.setClientID(row.get(Events.clientID));
        contextOneC.setApplicationName(applicationNameObject(row.get(Events.applicationName)));
        contextOneC.setComputerName(computerNameObject(row.get(Events.computerName)));
        contextOneC.setConnectID(row.get(Events.connectID).equals(Events.na) ? null : Long.parseLong(row.get(Events.connectID)));
        contextOneC.setSessionID(row.get(Events.sessionID).equals(Events.na) ? null : Long.parseLong(row.get(Events.sessionID)));
        contextOneC.setUser(userEventObject(row.get(Events.sessionID)));
        contextOneC.setDbPid(row.get(Events.dbpid) == null || row.get(Events.dbpid).equals(Events.na) ? null : Long.parseLong(row.get(Events.dbpid)));
        contextOneC.setText(row.get(Events.context));
        contextOneC.setHash(row.get(Events.context).hashCode());

        ContextOneC fromDb = contextOneCService.findByDateEventAndHashAndDuration(contextOneC.getDateEvent(),
                contextOneC.getHash(), contextOneC.getDuration());
        if (fromDb != null) return fromDb;

        ContextOneC event = contextOneCService.save(contextOneC);

        return event;

    }

    private Object rowToTimeOut(Map<String, String> row) {

        TimeoutEvent timeoutEvent = new TimeoutEvent();
        timeoutEvent.setDateEvent(dateEvent(row.get(Events.dateTime)));
        timeoutEvent.setDuration(Long.parseLong(row.get(Events.duration)));
        timeoutEvent.setProcessName(processNameObject(row.get(Events.processName)));
        timeoutEvent.setClientID(row.get(Events.clientID));
        timeoutEvent.setApplicationName(applicationNameObject(row.get(Events.applicationName)));
        timeoutEvent.setComputerName(computerNameObject(row.get(Events.computerName)));
        timeoutEvent.setConnectID(row.get(Events.connectID).equals(Events.na) ? null : Long.parseLong(row.get(Events.connectID)));
        timeoutEvent.setSessionID(row.get(Events.sessionID).equals(Events.na) ? null : Long.parseLong(row.get(Events.sessionID)));
        timeoutEvent.setUser(userEventObject(row.get(Events.usr)));
        timeoutEvent.setContextText(row.get(Events.context));
        timeoutEvent.setHash(row.get(Events.context).hashCode());
        timeoutEvent.setWaitConnections(row.get(Events.waitConnections).equals(Events.na) ? null : Long.parseLong(row.get(Events.waitConnections)));

        TimeoutEvent fromDb = timeOutEventService.findByDateEventAndDurationAndProcessNameAndClientID(timeoutEvent.getDateEvent(),
                timeoutEvent.getDuration(), timeoutEvent.getProcessName(), timeoutEvent.getClientID());
        if (fromDb != null) return fromDb;

        return timeOutEventService.save(timeoutEvent);
    }

    private Object rowToTlock(Map<String, String> row) {

        TlockEvent tlockEvent = new TlockEvent();
        tlockEvent.setDateEvent(dateEvent(row.get(Events.dateTime)));
        tlockEvent.setDuration(Long.parseLong(row.get(Events.duration)));
        tlockEvent.setProcessName(processNameObject(row.get(Events.processName)));
        tlockEvent.setClientID(row.get(Events.clientID));
        tlockEvent.setApplicationName(applicationNameObject(row.get(Events.applicationName)));
        tlockEvent.setComputerName(computerNameObject(row.get(Events.computerName)));
        tlockEvent.setConnectID(row.get(Events.connectID).equals(Events.na) ? null : Long.parseLong(row.get(Events.connectID)));
        tlockEvent.setSessionID(row.get(Events.sessionID).equals(Events.na) ? null : Long.parseLong(row.get(Events.sessionID)));
        tlockEvent.setUser(userEventObject(row.get(Events.usr)));
        tlockEvent.setContextText(row.get(Events.context));
        tlockEvent.setHash(row.get(Events.context).hashCode());
        tlockEvent.setRegions(regionsObjet(row.get(Events.regions)));
        String locks = row.get(Events.locks);
        if (locks.length() > 50000) locks = locks.substring(0, 49999);
        tlockEvent.setLocks(locks);
        tlockEvent.setWaitConnections(row.get(Events.waitConnections).equals(Events.na) || row.get(Events.waitConnections).isEmpty() ? null : Long.parseLong(row.get(Events.waitConnections)));
        TlockEvent fromDb = tlockService.findByDateEventAndDurationAndProcessNameAndClientID(tlockEvent.getDateEvent(),
                tlockEvent.getDuration(), tlockEvent.getProcessName(), tlockEvent.getClientID());
        if (fromDb != null) return fromDb;

        return tlockService.save(tlockEvent);
    }


    private Object rowToMssqlEvent(Map<String, String> row) {

        MssqlEvent mssqlEvent = new MssqlEvent();

        Long startMakeEvent = System.nanoTime();
        mssqlEvent.setDateEvent(dateEvent(row.get(Events.dateTime)));
        mssqlEvent.setDuration(Long.parseLong(row.get(Events.duration)));
        mssqlEvent.setProcessName(processNameObject(row.get(Events.processName)));
        mssqlEvent.setClientID(row.get(Events.clientID));
        mssqlEvent.setApplicationName(applicationNameObject(row.get(Events.applicationName)));
        mssqlEvent.setComputerName(computerNameObject(row.get(Events.computerName)));
        mssqlEvent.setConnectID(row.get(Events.connectID).equals(Events.na) ? null : Long.parseLong(row.get(Events.connectID)));
        mssqlEvent.setSessionID(row.get(Events.sessionID).equals(Events.na) ? null : Long.parseLong(row.get(Events.sessionID)));
        mssqlEvent.setUser(userEventObject(row.get(Events.usr)));
        //mssqlEvent.setAppId(row.get(Events.appID));
        mssqlEvent.setTransId(row.get(Events.trans).equals(Events.na) ? null : Long.parseLong(row.get(Events.trans)));
        mssqlEvent.setDbpid(row.get(Events.dbpid) == null || row.get(Events.dbpid).equals(Events.na) ? null : Long.parseLong(row.get(Events.dbpid)));
        Long startSql = System.nanoTime();
        mssqlEvent.setSql(sqlObject(row.get(Events.sql)));
        Long endsql = System.nanoTime();
        //System.out.println("find sql time " + (endsql - startSql));
        mssqlEvent.setRows(row.get(Events.rows).equals(Events.na) ? null : Long.parseLong(row.get(Events.rows)));
        mssqlEvent.setRowsAffected(row.get(Events.rowsAffected).equals(Events.na) ? null : Long.parseLong(row.get(Events.rowsAffected)));
        mssqlEvent.setContextText(row.get(Events.context));
        Long endMakeEvent = System.nanoTime();
        //System.out.println("make event time" + (endMakeEvent - startMakeEvent));

        Long startFindEvent = System.nanoTime();
//        MssqlEvent fromDb = mssqlEventService.findByDateAndDuration(mssqlEvent.getDateEvent()
//                , mssqlEvent.getDuration());
        Long endFindEvent = System.nanoTime();
        //System.out.println("find event time" + (endFindEvent - startFindEvent));
        //if (fromDb != null) return fromDb;
        Long startSaveEvent = System.nanoTime();
        MssqlEvent savedMssql = mssqlEventService.save(mssqlEvent);
        Long endSaveEvent = System.nanoTime();
        //System.out.println("save event time " + (endFindEvent - startFindEvent));
        return savedMssql;

    }

    private Sql sqlObject(String field) {
        if (field.equals(Events.na)) return null;

        Sql sql = new Sql();
        sql.setHash(field.trim().hashCode());
        sql.setQuery(field.trim());
        String md5Hex = DigestUtils
                .md5Hex(field.trim()).toUpperCase();
        sql.setMd5hash(md5Hex);

        Sql sqlFromDb = sqlService.findByHashAndMd5hash(field.trim().hashCode(), sql.getMd5hash());
        if (sqlFromDb != null) return sqlFromDb;

        return sqlService.save(sql);

    }

    private UserEvent userEventObject(String field) {
        if (field.equals(Events.na)) return null;

        UserEvent userEvent = userEventService.findByName(field.trim());
        if (userEvent != null) return userEvent;

        userEvent = new UserEvent();
        userEvent.setUserName(field.trim());

        return userEventService.save(userEvent);
    }

    private ComputerName computerNameObject(String field) {
        if (field.equals(Events.na)) return null;

        ComputerName computerName = computerNameService.findByName(field.trim());
        if (computerName != null) return computerName;

        computerName = new ComputerName();
        computerName.setCompName(field.trim());

        return computerNameService.save(computerName);
    }

    private ApplicationName applicationNameObject(String field) {

        if (field==null || field.equals(Events.na)) return null;

        ApplicationName applicationName = applicationNameService.findByName(field.trim());
        if (applicationName != null) return applicationName;

        applicationName = new ApplicationName();
        applicationName.setAppName(field.trim());

        return applicationNameService.save(applicationName);
    }

    private ProcessName processNameObject(String field) {

        if (field.equals(Events.na) || field.isEmpty() || field == null) return null;

        ProcessName processName = processNameService.findByName(field.trim());
        if (processName != null) return processName;

        processName = new ProcessName();
        processName.setProc(field.trim());


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
