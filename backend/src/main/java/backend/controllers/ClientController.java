package backend.controllers;

import backend.models.Client;
import backend.repositories.ClientRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * For each client with a licence without a server
     * Check if there is a available server for him.
     */
    public void licensesVerificationWithServer() {

    }

    @GetMapping("/clients-info")
    public List<Client> getClientsInformation() {
        return this.clientRepository.getClients();
    }

    @GetMapping("/clients-info/{id}")
    public List<Client> getClientInformation(String client_id) {
        System.out.println(client_id);
        return this.clientRepository.getClient(client_id);
    }



}
