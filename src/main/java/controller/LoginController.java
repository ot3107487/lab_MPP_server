package controller;

import controller.formatters.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ILoginService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class LoginController {
    private final ILoginService loginService;

    @Autowired
    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login")
    public boolean login(@RequestBody Credentials credentials, HttpServletResponse response) {
        boolean status=loginService.login(credentials.getUsername(), credentials.getPassword());
        if (!status) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 - auth failed
            return false;
        }
        response.setStatus(HttpServletResponse.SC_OK);// 200
        return true;
    }
}
