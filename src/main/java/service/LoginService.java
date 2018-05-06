package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepositoryJPA;

@Service
public class LoginService implements ILoginService {
    final
    private UserRepositoryJPA userRepositoryJPA;

    @Autowired
    public LoginService(UserRepositoryJPA userRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
    }

    public boolean login(String userName, String password) {
        User user=userRepositoryJPA.getByPasswordAndUserName(password,userName);
        return user!=null;
    }
}
