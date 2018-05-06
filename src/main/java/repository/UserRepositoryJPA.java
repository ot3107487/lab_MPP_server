package repository;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJPA extends JpaRepository<User,Integer> {
    User getByPasswordAndUserName(String password,String userName);
}
