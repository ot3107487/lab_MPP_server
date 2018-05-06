package service;

import repository.UserRepository;
import repository_utils.FillTables;

public class LoginService {
    private UserRepository repository;

    public LoginService(UserRepository repository) {
        this.repository = repository;
        FillTables.fillUsers(repository);
    }

    public boolean login(String userName, String password) {

        return repository.login(userName, password);
    }
}
