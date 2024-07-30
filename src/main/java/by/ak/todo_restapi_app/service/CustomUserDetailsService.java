package by.ak.todo_restapi_app.service;

import by.ak.todo_restapi_app.entity.User;
import by.ak.todo_restapi_app.exceptions.customException.UserNotFoundException;
import by.ak.todo_restapi_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() -> {
            log.error("Error fetching user by username: {} user not found.",username);
            return new UserNotFoundException("User with username: {} not found.");
        });

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("read")
                .build();
    }
}
