package model.fildClass;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class ComputerName {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cname_seq")
    @SequenceGenerator(name = "cname_seq", sequenceName = "cname_seq")
    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComputerName)) return false;
        ComputerName that = (ComputerName) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
