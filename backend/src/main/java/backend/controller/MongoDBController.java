package backend.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MongoDBController {
    public static MongoClient mongoClient;
    public static MongoDatabase database;
    public MongoCollection<BasicDBObject> serversCollection;
    public static MongoCollection<BasicDBObject> licensesCollection;
    public static MongoCollection<BasicDBObject> requestsCollection;

    public MongoDBController() {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("BootstrapServerDB");
        serversCollection = database.getCollection("Servers", BasicDBObject.class);
        licensesCollection = database.getCollection("Licenses", BasicDBObject.class);
        requestsCollection = database.getCollection("Requests", BasicDBObject.class);
    }

//    @PostMapping("/request")
//    public void addRequest(String customer_id, String location, String license_key) {
//        BasicDBObject document = new BasicDBObject();
//        document.put("customer_id", customer_id);
//        document.put("location", location);
//        document.put("license_key", license_key);
//        requestsCollection.insertOne(document);
//        requestVerification(document);
//    }


    public void removeRequest(BasicDBObject document) {
        if (requestsCollection.deleteOne(Filters.eq("license_key", document.get("license_key"))).wasAcknowledged()) {
            System.out.println("request deleted");
        } else {
            System.out.println("request not deleted");
        }
    }

    public void setExpirationTimer(BasicDBObject document) {
        // TODO
    }

    public void addServer(String server_id, String server_ip, int capacity, String location) {
        BasicDBObject document = new BasicDBObject();
        document.put("server_id", server_id);
        document.put("server_ip_address", server_ip);
        document.put("clients_capacity", capacity);
        document.put("location", location);
        this.serversCollection.insertOne(document);
    }

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

        BasicDBObject document = new BasicDBObject();
        document.put("license_id", license_id);
        document.put("client_id", null);
        document.put("license_key", license_key);
        document.put("license_expiration_time", license_expiration_time);
        licensesCollection.insertOne(document);
    }
}
