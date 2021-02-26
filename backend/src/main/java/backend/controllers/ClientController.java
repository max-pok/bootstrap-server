package backend.controllers;

import backend.models.Client;
import backend.repositories.ClientRepository;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    public List<Client> getClientInformation(@PathVariable("id") String client_id) {
        return this.clientRepository.getClient(client_id);
    }



}
