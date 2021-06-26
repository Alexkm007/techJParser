package ru.alexkm07.techjparser.model.event;

import lombok.Data;
import ru.alexkm07.techjparser.model.fildClass.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class MssqlEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mssql_seq")
    @SequenceGenerator(name = "mssql_seq", sequenceName = "mssql_seq")
    private Long id;
    private LocalDateTime dateEvent;
    private Long duration;
    @ManyToOne
    private ProcessName processName;
    private String clientID;
    @ManyToOne
    private ApplicationName applicationName;
    @ManyToOne
    private ComputerName computerName;
    private Long connectID;
    private Long sessionID;
    @ManyToOne
    private UserEvent user;
    private Long transId;
    private Long dbPid;
    @ManyToOne
    @Column(length = 8000)
    private Sql sql;
    private Long rows;
    private Long rowsAffected;
    private String appId;
    @Column(length = 500000)
    private String contextText;
}
