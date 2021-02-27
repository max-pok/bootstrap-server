package backend.repositories;

import org.springframework.stereotype.Repository;
import backend.utilities.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServerRepository {

    /**
     * @returns all the distinct locations from the server collection.
     */
    public List<String> getDistinctLocations() {
        return MongoDB.serversCollection.distinct("location", String.class).into(new ArrayList<>());
    }

}
