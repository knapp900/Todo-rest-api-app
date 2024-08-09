package by.ak.todo_restapi_app.controller;

import by.ak.todo_restapi_app.dto.UserDTO;
import by.ak.todo_restapi_app.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RegistrationController {

    private final RegistrationService service;

    @PostMapping("/api/v1/signIn")
    public void registration(@RequestBody UserDTO user){

        this.service.registrationUser(user);
    }

}
