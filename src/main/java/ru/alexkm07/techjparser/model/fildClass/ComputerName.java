package ru.alexkm07.techjparser.model.fildClass;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(indexes = {
        @Index(name = "compName", columnList = "compName")
})
public class ComputerName {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cname_seq")
    @SequenceGenerator(name = "cname_seq", sequenceName = "cname_seq")
    private Long id;
    @Column(length = 50)
    private String compName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComputerName)) return false;
        ComputerName that = (ComputerName) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCompName(), that.getCompName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCompName());
    }
}
