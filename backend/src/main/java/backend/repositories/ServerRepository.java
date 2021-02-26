package backend.repositories;

import com.mongodb.client.DistinctIterable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServerRepository {

    public List<String> getDistinctLocations() {
        DistinctIterable<String> locations = MongoDB.serversCollection.distinct("location", String.class);
        List<String> locationsList = new ArrayList<>();
        for (String location : locations) {
            locationsList.add(location);
        }
        return locationsList;
    }
}
