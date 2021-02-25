package backend.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    public static MongoClient mongoClient = MongoClients.create();
    public static MongoDatabase database = mongoClient.getDatabase("BootstrapServerDB");
    public static MongoCollection<BasicDBObject> serversCollection = database.getCollection("Servers", BasicDBObject.class);
    public static MongoCollection<BasicDBObject> licensesCollection = database.getCollection("Licenses", BasicDBObject.class);
    public static MongoCollection<BasicDBObject> requestsCollection = database.getCollection("Requests", BasicDBObject.class);
    public static MongoCollection<BasicDBObject> clientsCollection = database.getCollection("Clients", BasicDBObject.class);
}
