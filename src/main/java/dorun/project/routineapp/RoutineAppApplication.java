package dorun.project.routineapp;

import dorun.project.routineapp.config.properties.AppProperties;
import dorun.project.routineapp.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
public class RoutineAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoutineAppApplication.class, args);
    }

}
