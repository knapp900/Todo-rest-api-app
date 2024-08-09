package by.ak.todo_restapi_app.service;

import by.ak.todo_restapi_app.dto.UserDTO;
import by.ak.todo_restapi_app.exceptions.customException.UserRegistrationException;
import by.ak.todo_restapi_app.exceptions.customException.UserServiceException;
import by.ak.todo_restapi_app.repository.UserRepository;
import by.ak.todo_restapi_app.service.impl.mapper.UserDTOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final UserRepository repository;
    private final UserDTOMapper userMapper;

    public void registrationUser(UserDTO user) {

        try {
            boolean isExist = this.repository.findByUsername(user.username()).isPresent();

            if (isExist) {
                throw new UserRegistrationException(String.format(
                        "User with username: %s already exist.", user.username()));

            } else {
                this.repository.save(userMapper.toEntity(user));
            }
        } catch (UserRegistrationException e) {
            log.error("User with username: %s already exist.", user.username(), e);
            throw new UserServiceException(e.getMessage());
        } catch (Exception e) {
            log.error("Registration user with username: {} failed.", user.username(), e);
            throw new UserServiceException(String.format
                    ("Registration user with username: %s failed.", user.username()), e);
        }
    }

}
