package backend.repositories;

import backend.models.Request;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.springframework.stereotype.Repository;

@Repository
public class RequestRepository {

    public void saveRequest(Request request) {
        MongoDB.requestsCollection.replaceOne(Filters.and(Filters.eq("customer_id", request.customer_id),
                Filters.eq("location", request.location),
                Filters.eq("license_key", request.license_key)), request, new ReplaceOptions().upsert(true));
    }
}
