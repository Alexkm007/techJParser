package ru.alexkm07.techjparser.model.fildClass;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
public class ContextOneC {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "context_seq")
    @SequenceGenerator(name = "context_seq", sequenceName = "context_seq")
    private Long id;
    private Integer hash;
    @Column(length = 2048)
    private String text;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContextOneC)) return false;
        ContextOneC that = (ContextOneC) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getHash(), that.getHash()) && Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHash(), getText());
    }
}
