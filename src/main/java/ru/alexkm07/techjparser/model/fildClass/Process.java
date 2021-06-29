package ru.alexkm07.techjparser.model.fildClass;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(indexes = {
        @Index(name = "procName", columnList = "procName")
})
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proc_seq")
    @SequenceGenerator(name = "proc_seq", sequenceName = "proc_seq")
    private Long id;
    @Column(length = 50)
    private String procName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Process)) return false;
        Process that = (Process) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getProcName(), that.getProcName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProcName());
    }
}
