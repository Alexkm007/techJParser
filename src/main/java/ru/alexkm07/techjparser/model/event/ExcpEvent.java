package ru.alexkm07.techjparser.model.event;

import lombok.Data;
import ru.alexkm07.techjparser.model.fildClass.Process;
import ru.alexkm07.techjparser.model.fildClass.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ExcpEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "excp_seq")
    @SequenceGenerator(name = "excp_seq", sequenceName = "excp_seq")
    private Long id;
    private LocalDateTime dateEvent;
    private String process;
    @ManyToOne
    private ProcessName processName;
    private String clientID;
    @ManyToOne
    private ApplicationName applicationName;
    @ManyToOne
    private ComputerName computerName;
    private Long connectId;
    @Column(length = 2000)
    private String exception;
    @Column(length = 5000)
    private String descr;
    private Integer hash;
}
