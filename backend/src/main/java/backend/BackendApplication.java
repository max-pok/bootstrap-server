package backend;

import backend.controller.User;
import backend.controller.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Timer;
import java.util.stream.Stream;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(BackendApplication.class, args);
		new Thread(new RequestsAuthentication()).start();
//		new Timer().schedule(new RequestsAuthentication(), 0, 1000);
//		while (true) {
//			Thread.sleep(1000);
//		}
	}

}
