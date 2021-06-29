package ru.alexkm07.techjparser.model.event;

import lombok.Data;
import ru.alexkm07.techjparser.model.fildClass.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(indexes = {
        @Index(name = "duration", columnList = "duration"),
        @Index(name = "processName", columnList = "process_name_id")
})
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
    private Long dbpid;
    @ManyToOne
    private UserEvent user;
    private Long transId;
    @ManyToOne
    private Sql sql;
    private Long rows;
    private Long rowsAffected;
    @Column(length = 500000)
    private String contextText;
}
