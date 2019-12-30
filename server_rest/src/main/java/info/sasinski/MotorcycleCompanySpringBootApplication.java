package info.sasinski;

import info.sasinski.service.SeedDataService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor

@SpringBootApplication
public class MotorcycleCompanySpringBootApplication implements CommandLineRunner {

    final SeedDataService _seedDataService;

    public static void main(String[] args) {
        SpringApplication.run(MotorcycleCompanySpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        _seedDataService.seed();
    }
}
