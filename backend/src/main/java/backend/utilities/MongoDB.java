package backend.utilities;

import backend.models.Client;
import backend.models.License;
import backend.models.Server;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDB {

    private MongoDB() {}

    public static final MongoClient mongoClient = initMongoClient();
    public static final MongoDatabase database = mongoClient.getDatabase("BootstrapServerDB");
    public static final MongoCollection<Server> serversCollection = database.getCollection("Servers", Server.class);
    public static final MongoCollection<License> licensesCollection = database.getCollection("Licenses", License.class);
    public static final MongoCollection<Client> clientsCollection = database.getCollection("Clients", Client.class);

    public static MongoClient initMongoClient() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost"))
                .codecRegistry(codecRegistry)
                .build());
    }
}
