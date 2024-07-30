package by.ak.todo_restapi_app.service;

import by.ak.todo_restapi_app.entity.User;
import by.ak.todo_restapi_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final UserRepository repository;

    public void registrationUser(User user){
        this.repository.save(user);
    }

}
