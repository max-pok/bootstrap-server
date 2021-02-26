package backend.threads;

import backend.models.Client;
import backend.models.Server;
import backend.repositories.MongoDB;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class ServerAllocation implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(ServerAllocation.class.getName());

    public ServerAllocation() {

    }

    @Override
    public void run() {
        while (true) {
            try {
                allocateServerToClient();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  For each pending account (with license and without server id):
     * 1.   Find list of servers with the same location of the account
     * 2.   For each server in this list:
     *  2.1     Find amount of accounts use the server.
     *  2.2     If amount == client capacity (server is full)
     *  2.3     if amount < client capacity (server has space) => add the server to account information
     *          and start the timer for his license key.
     */
    public synchronized void allocateServerToClient() {
        List<Client> clientList = MongoDB.clientsCollection.find(Filters.eq("server_id", "")).into(new ArrayList<>());;

        for (Client client: clientList) {
            List<Server> serverList = MongoDB.serversCollection.find(Filters.eq("location", client.getLocation())).into(new ArrayList<>());
            for (Server server: serverList) {
                if (server.getCurrent_clients_capacity() < server.getClients_capacity()) {
                    MongoDB.serversCollection.updateOne(
                            Filters.eq("server_id", server.getServer_id()),
                            Updates.set("current_client_capacity", server.getCurrent_clients_capacity() + 1));

                    client.setClients_capacity(server.getClients_capacity());
                    client.setServer_id(server.getServer_id());
                    MongoDB.clientsCollection.replaceOne(Filters.eq("license_key", client.getLicense_key()), client);
                    break;
                }
            }
        }
    }
}
