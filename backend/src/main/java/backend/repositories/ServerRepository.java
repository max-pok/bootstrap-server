package backend.repositories;

import org.springframework.stereotype.Repository;

import backend.models.Client;
import backend.models.Server;
import backend.threads.LicenseExpiration;
import backend.utilities.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.mongodb.client.model.Filters;

@Repository
public class ServerRepository {

    private static final String SERVER_ID = "server_id";
    private static final String LOCATION = "location";
    private static final Logger LOGGER = Logger.getLogger(ServerRepository.class.getName());

    /**
     * @returns all the distinct locations from the server collection.
     */
    public List<String> getDistinctLocations() {
        return MongoDB.serversCollection.distinct(LOCATION, String.class).into(new ArrayList<>());
    }

    /**
     *  allocate a server to a panding client [with a valid license key and without a server]
     *  with a given server location.
     */
    public static synchronized void allocateServersToClients(String location) {
        List<Client> clientList = MongoDB.clientsCollection.find(
            Filters.and(Filters.eq(SERVER_ID, ""), 
            Filters.ne("license_expiration_time", 0),
            Filters.eq(LOCATION, location))).into(new ArrayList<>());
        
        for (Client client: clientList) {
            allocateServer(client);
        }
    }


    /**
     *  1. Find list of servers with the same location of the account.
     *  2.   For each server in this list:
     *    2.1     Find amount of accounts use the server.
     *    2.2     If amount == client capacity (server is full).
     *    2.3     if amount < client capacity (server has space) => add the server to account information
     *            and start the timer for his license key.
     */
    public static synchronized void allocateServer(Client client) {
        List<Server> servers = MongoDB.serversCollection.find(Filters.eq(LOCATION, client.getLocation())).into(new ArrayList<>());

        // check if there are no servers with client location
        if (servers.isEmpty()) {
            LOGGER.warning("No available servers in " + client.getLocation());
            return;
        }
        
        for (Server server: servers) {
            int current_size = MongoDB.clientsCollection.find(Filters.eq(SERVER_ID, server.getServer_id())).into(new ArrayList<>()).size();
            if (current_size < server.getClients_capacity()) {
                client.setClients_capacity(server.getClients_capacity());
                client.setServer_id(server.getServer_id());
                MongoDB.clientsCollection.replaceOne(Filters.eq("license_key", client.getLicense_key()), client);

                new Thread(new LicenseExpiration(client)).start();
                return;
            }
        }
        LOGGER.warning("No available servers in " + client.getLocation());
    }

    
    /**
     * In case the server is rebooted the function will start all the timers for the
     * clients with a valid license key and an existing server id.
     */
    public void startClientsLicenseExpirationThreads() {
        List<Client> clientList = MongoDB.clientsCollection.find(Filters.and(Filters.ne(SERVER_ID, ""), Filters.ne("license_expiration_time", 0))).into(new ArrayList<>());
        
        for (Client client: clientList) {
            new Thread(new LicenseExpiration(client)).start();
        }
    }

    /**
     * In case the server is rebooted the function will try to allocate
     * the valid clients without a server a server.
     */
    public void initServerAllocation() {
        List<String> locations = getDistinctLocations();

        for (String location: locations) {
            allocateServersToClients(location);
        }
    }

}
