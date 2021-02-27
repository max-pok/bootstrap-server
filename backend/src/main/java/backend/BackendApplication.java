package backend;

import backend.threads.LicenseExpiration;
import backend.threads.ServerAllocation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		initProcesses();
	}

	public static void initProcesses() {
		ServerAllocation serverAllocation = new ServerAllocation();
		new Thread(serverAllocation).start();
	}

}
