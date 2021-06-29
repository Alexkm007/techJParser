package ru.alexkm07.techjparser.model.fildClass;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(indexes = {
        @Index(name = "appName", columnList = "appName")
})
public class ApplicationName {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appname_seq")
    @SequenceGenerator(name = "appname_seq", sequenceName = "appname_seq")
    private Long id;
    @Column(length = 50)
    private String appName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationName)) return false;
        ApplicationName that = (ApplicationName) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getAppName(), that.getAppName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAppName());
    }
}
