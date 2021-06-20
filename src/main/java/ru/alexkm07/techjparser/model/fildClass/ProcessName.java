package ru.alexkm07.techjparser.model.fildClass;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class ProcessName {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procname_seq")
    @SequenceGenerator(name = "procname_seq", sequenceName = "procname_seq")
    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcessName)) return false;
        ProcessName that = (ProcessName) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
