package backend.repositories;

import com.mongodb.client.DistinctIterable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServerRepository {

    public List<String> getDistinctLocations() {
        return MongoDB.serversCollection.distinct("location", String.class).into(new ArrayList<>());
    }
}
