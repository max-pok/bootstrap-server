package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import backend.repositories.ServerRepository;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		ServerRepository.startClientsLicenseExpirationThreads();
		ServerRepository.allocateServersToClients();
	}
}
