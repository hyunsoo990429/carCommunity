package carCommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarCommunityApplication.class, args);
	}

}
