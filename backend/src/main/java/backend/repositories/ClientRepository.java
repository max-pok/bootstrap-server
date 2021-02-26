package backend.repositories;

import backend.models.Client;
import backend.models.License;
import com.mongodb.client.model.Filters;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientRepository {

    public void initClientInformation(License license, String location) {
        Client client = new Client(license.getClient_id(), license.getLicense_key(), license.getLicense_expiration_time(), "", 0, location);
        this.addClient(client);
    }

    public void addClient(Client client) {
        MongoDB.clientsCollection.insertOne(client);
    }

    public List<Client> getClients() {
        return MongoDB.clientsCollection.find().into(new ArrayList<>());
    }

    public List<Client> getClient(String client_id) {
        return MongoDB.clientsCollection.find(Filters.eq("client_id", client_id)).into(new ArrayList<>());
    }
}
