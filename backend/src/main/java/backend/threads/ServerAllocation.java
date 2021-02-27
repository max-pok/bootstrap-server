package backend.threads;

import backend.models.Client;
import backend.models.Server;
import backend.utilities.*;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *  Runnable.
 *  Each second, it checks if there is available server to allocate to valid user.
 */
public class ServerAllocation implements Runnable {

    private static final String SERVER_ID = "server_id";
    private static final Logger LOGGER = Logger.getLogger(ServerAllocation.class.getName());


    @Override
    public void run() {
        LOGGER.info(Thread.currentThread().getName() + ": started. [Server Allocation]");
        // starts timer for valid licenses with server id
        resetClientLicenseExpirationThreads();
        while (true) {
            try {
                allocateServerToClient();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  For each pending account (with valid license and without server id):
     *  1.   Find list of servers with the same location of the account.
     *  2.   For each server in this list:
     *      2.1     Find amount of accounts use the server.
     *      2.2     If amount == client capacity (server is full).
     *      2.3     if amount < client capacity (server has space) => add the server to account information
     *              and start the timer for his license key.
     */
    public synchronized void allocateServerToClient() {
        List<Client> clientList = MongoDB.clientsCollection.find(
                Filters.and(Filters.eq(SERVER_ID, ""), Filters.ne("license_expiration_time", 0))).into(new ArrayList<>());

         for (Client client: clientList) {
             List<Server> serverList = MongoDB.serversCollection.find(Filters.eq("location", client.getLocation())).into(new ArrayList<>());
             for (Server server: serverList) {
                 int current_size = MongoDB.clientsCollection.find(Filters.eq(SERVER_ID, server.getServer_id())).into(new ArrayList<>()).size();
                 if (current_size < server.getClients_capacity()) {
                     client.setClients_capacity(server.getClients_capacity());
                     client.setServer_id(server.getServer_id());

                     MongoDB.clientsCollection.replaceOne(Filters.eq("license_key", client.getLicense_key()), client);
                     new Thread(new LicenseExpiration(client)).start();
                     break;
                 }
             }
         }
    }

    /**
     * In case the server is rebooted the function will start all the timers for the
     * clients with a valid license key and an existing server id.
     */
    public synchronized void resetClientLicenseExpirationThreads() {
        List<Client> clientList = MongoDB.clientsCollection.find(Filters.and(Filters.ne(SERVER_ID, ""), Filters.ne("license_expiration_time", 0))).into(new ArrayList<>());
        
        for (Client client: clientList) {
            new Thread(new LicenseExpiration(client)).start();
        }
    }
}