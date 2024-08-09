package by.ak.todo_restapi_app;

import by.ak.todo_restapi_app.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class TodoRestApiAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoRestApiAppApplication.class, args);
    }

    //TODO (High) change JPA to JDBC in different branch
    //TODO (High) Handle test of controllers
    //TODO (High) Fill README.MD file
    //TODO (High) Take out sensitive data of application.property and put them in to .env file and add .env to .gitignore
}

