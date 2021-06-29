package ru.alexkm07.techjparser.model.event;

import lombok.Data;
import ru.alexkm07.techjparser.model.fildClass.*;
import ru.alexkm07.techjparser.model.fildClass.Process;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class TlockEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tlock_seq")
    @SequenceGenerator(name = "tlock_seq", sequenceName = "tlock_seq")
    private Long id;
    private LocalDateTime dateEvent;
    private Long duration;
    @ManyToOne
    private ProcessName processName;
    @ManyToOne
    private Process process;
    private String clientID;
    @ManyToOne
    private ApplicationName applicationName;
    @ManyToOne
    private ComputerName computerName;
    private Long connectID;
    private Long sessionID;
    @ManyToOne
    private UserEvent user;
    private Long waitConnections;
    @Column(length = 500000)
    private String contextText;
    @Column(length = 50000)
    private String locks;
    @ManyToOne
    private Regions regions;
    private Integer hash;
}
