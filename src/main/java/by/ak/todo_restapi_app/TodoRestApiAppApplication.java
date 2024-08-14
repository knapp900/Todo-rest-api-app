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

}

