package by.elmax19.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TournamentsSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TournamentsSpringBootApplication.class, args);
    }

}
