package repository;

import model.fildClass.ComputerName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerNameRepository extends JpaRepository<ComputerName,Long> {
}
