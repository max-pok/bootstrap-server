package backend.repositories;

import backend.models.Client;
import backend.models.Server;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.count;
import static com.mongodb.client.model.Aggregates.match;

@Repository
public class ServerRepository {

    private final ClientRepository clientRepository;

    public ServerRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<String> getDistinctLocations() {
        return MongoDB.serversCollection.distinct("location", String.class).into(new ArrayList<>());
    }

}
