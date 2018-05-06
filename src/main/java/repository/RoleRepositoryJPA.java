package repository;

import model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositoryJPA extends JpaRepository<Role,Integer> {
}
