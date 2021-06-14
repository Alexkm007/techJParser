package repository.event;

import model.event.MssqlEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MssqlEventRepository extends JpaRepository<MssqlEvent,Long> {

    MssqlEvent findByDateEvent(LocalDateTime localDateTime);

}
