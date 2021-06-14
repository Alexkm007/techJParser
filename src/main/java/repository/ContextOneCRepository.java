package repository;

import model.fildClass.ContextOneC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContextOneCRepository extends JpaRepository<ContextOneC,Long> {

    ContextOneC findByHashAndText(Integer hash, String text);
}
