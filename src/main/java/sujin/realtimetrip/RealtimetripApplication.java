package sujin.realtimetrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RealtimetripApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealtimetripApplication.class, args);
	}

}
