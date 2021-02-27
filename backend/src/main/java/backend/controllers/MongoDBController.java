package backend.controllers;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import backend.utilities.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MongoDBController {

    /**
     * Dev tool for presentation purposes.
     */
    @PostMapping("/clients-info/dev/319475513/reset-all")
    public void resetAll() {
        // remove all the clients
        MongoDB.clientsCollection.deleteMany(new Document());
        // clear all the clients ids from license collection
        MongoDB.licensesCollection.updateMany(Filters.ne("client_id", null), Updates.set("client_id", null));
    }
}