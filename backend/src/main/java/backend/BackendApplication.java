package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(BackendApplication.class, args);
//		new Thread(new RequestsAuthentication()).start();

	}

}
