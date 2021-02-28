package backend.repositories;

import backend.utilities.*;
import backend.models.Client;
import backend.models.License;
import com.mongodb.client.model.Filters;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientRepository {

    /**
     * Initializes the new client from a given license and location.
     */
    public void initClientInformation(License license, String location) {
        Client client = new Client(license.getClient_id(), license.getLicense_key(), license.getLicense_expiration_time(), "", 0, location);
        this.addClient(client);
    }

    /**
     * Adds a new client to the client collection.
     */
    public void addClient(Client client) {
        MongoDB.clientsCollection.insertOne(client);
        // try to allocate the client a server
        ServerRepository.allocateServer(client);
    }

    /**
     * @returns all the clients from the clients collection.
     */
    public List<Client> getClients() {
        return MongoDB.clientsCollection.find().into(new ArrayList<>());
    }

    /**
     * @returns a client classes (client information) based on a given client id.
     */
    public List<Client> getClient(String client_id) {
        return MongoDB.clientsCollection.find(Filters.eq("client_id", client_id)).into(new ArrayList<>());
    }
}
