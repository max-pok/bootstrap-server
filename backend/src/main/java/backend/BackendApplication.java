package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import backend.repositories.ServerRepository;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		start();
	}

	public static void start() {
		ServerRepository serverRepository = new ServerRepository();
		serverRepository.startClientsLicenseExpirationThreads();
		serverRepository.initServerAllocation();
	}
}
