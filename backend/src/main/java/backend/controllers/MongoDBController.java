package backend.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.repositories.MongoDB;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MongoDBController {

    // public void addServer(String server_id, String server_ip, int capacity, String location) {
    //     BasicDBObject document = new BasicDBObject();
    //     document.put("server_id", server_id);
    //     document.put("server_ip_address", server_ip);
    //     document.put("clients_capacity", capacity);
    //     document.put("location", location);
    //     MongoDB.serversCollection.insertOne(document);
    // }

    public void addLicense(String license_id, String license_key, int license_expiration_time) {
        //        mongoDB.addLicense("2",  "NXCPX-TKJ5H-N3U8N", 2);
        //        mongoDB.addLicense("3", "5HFDD-ZPTAM-0OE4Z", 2);
        //        mongoDB.addLicense("4", "Z48VN-RF6PV-UXA88", 3);
        //        mongoDB.addLicense("5",  "1F2ND-XOLGO-OWNNV", 4);
        //        mongoDB.addLicense("6", "1A39P-YJ2C8-O9K1X", 4);
        //        mongoDB.addLicense("7",  "7KGVF-Q7H55-0B38T", 5);
        //        mongoDB.addLicense("8",  "GDY99-45UFQ-HRM1X", 10);
        //        mongoDB.addLicense("9",  "4MAYC-HW32K-KQBQ7", 15);
        //        mongoDB.addLicense("10",  "GT8QR-P34WJ-BCG0J", 30);

        // BasicDBObject document = new BasicDBObject();
        // document.put("license_id", license_id);
        // document.put("client_id", null);
        // document.put("license_key", license_key);
        // document.put("license_expiration_time", license_expiration_time);
        // licensesCollection.insertOne(document);
    }

    @PostMapping("/clients-info/dev/319475513/reset-all")
    public void resetAll() {
        // remove all the requests
        MongoDB.requestsCollection.deleteMany(new Document());
        // remove all the clients
        MongoDB.clientsCollection.deleteMany(new Document());
        // clear all the clients ids from license collection
        MongoDB.licensesCollection.updateMany(Filters.ne("client_id", null), Updates.set("client_id", null));
    }
}
