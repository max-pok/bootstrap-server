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
     * @returns the frontend all client information based on a given id.
     */
    @GetMapping("/clients-info/{id}")
    public List<Client> getClientInformation(@PathVariable("id") String client_id) {
        return this.clientRepository.getClient(client_id);
    }


    // @GetMapping("/clients-info")
    // public List<Client> getClientsInformation() {
    //     return this.clientRepository.getClients();
    // }
}
