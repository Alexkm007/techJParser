package ru.alexkm07.techjparser.model.fildClass;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class Regions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proc_seq")
    @SequenceGenerator(name = "proc_seq", sequenceName = "proc_seq")
    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Regions)) return false;
        Regions that = (Regions) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
