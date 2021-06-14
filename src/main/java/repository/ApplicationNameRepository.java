package repository;

import model.fildClass.ApplicationName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationNameRepository extends JpaRepository<ApplicationName,Long> {

    ApplicationName findByName(String name);

}
