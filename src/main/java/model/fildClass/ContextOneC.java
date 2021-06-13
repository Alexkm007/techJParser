package model.fildClass;

import lombok.Data;

import javax.persistence.*;
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
