package ru.alexkm07.techjparser.model.fildClass;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(indexes = {
        @Index(name = "hash", columnList = "hash")
        ,@Index(name = "md5hash", columnList = "md5hash")
})
public class Sql {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sql_seq")
    @SequenceGenerator(name = "sql_seq", sequenceName = "sql_seq")
    private Long id;
    private Integer hash;
    @Column(length = 500000)
    private String query;
    @Column(length = 32)
    private String md5hash;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sql)) return false;
        Sql sql = (Sql) o;
        return Objects.equals(getId(), sql.getId()) && Objects.equals(getHash(), sql.getHash()) && Objects.equals(getQuery(), sql.getQuery());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHash(), getQuery());
    }
}
