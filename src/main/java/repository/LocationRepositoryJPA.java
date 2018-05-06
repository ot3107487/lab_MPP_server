package repository;

import model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepositoryJPA extends JpaRepository<Location,Integer> {
}
