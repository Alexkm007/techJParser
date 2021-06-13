package repository;

import model.fildClass.Sql;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlRepository extends JpaRepository<Sql,Long> {
}
