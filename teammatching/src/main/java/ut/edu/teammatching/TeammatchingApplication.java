package ut.edu.teammatching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // ✅ Bật Scheduled task
public class TeammatchingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeammatchingApplication.class, args);
    }
}
