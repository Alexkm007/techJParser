package model.fildClass;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
public class ApplicationName {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appname_seq")
    @SequenceGenerator(name = "appname_seq", sequenceName = "appname_seq")
    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationName)) return false;
        ApplicationName that = (ApplicationName) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
