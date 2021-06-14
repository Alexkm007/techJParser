package repository;

import model.fildClass.ProcessName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessNameRepository extends JpaRepository<ProcessName,Long> {
    ProcessName findByName(String name);
}
