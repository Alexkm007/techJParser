package repository.event;

import model.event.MssqlEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MssqlEventRepository extends JpaRepository<MssqlEvent,Long> {
}
