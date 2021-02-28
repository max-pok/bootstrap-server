package backend.threads;

import backend.models.Client;
import backend.repositories.ServerRepository;
import backend.utilities.*;
import com.mongodb.client.model.Filters;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Runnable.
 *  Each minute, it reduces the license expiration time for active users [with valid license and a server].
 *  The runnable class is created for a valid client.
 */
public class LicenseExpiration implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(LicenseExpiration.class.getName());

    private Client client;

    public LicenseExpiration(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        LOGGER.info(Thread.currentThread().getName() + ": started. [LicenseExpiration]");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.MINUTES.sleep(1);
                updateExpirationTime();
            } catch (InterruptedException e) {
                LOGGER.info(Thread.currentThread().getName() + " finished. [LicenseExpiration]");
                break;
            }
        }
    }

    /**
     *  Each minute the function reduces the license expiration time.
     *  If the expiration time reached 0, the function removes the server id and capacity from the client
     *  and stops the update thread.
     */
    public synchronized void updateExpirationTime() {
        client.setLicense_expiration_time(client.getLicense_expiration_time() - 1);
        MongoDB.clientsCollection.replaceOne(Filters.eq("license_key", client.getLicense_key()), client);
        
        if (client.getLicense_expiration_time() == 0) {
            // remove server from client
            client.setServer_id("");
            client.setClients_capacity(0);
            // update collection
            MongoDB.clientsCollection.replaceOne(Filters.eq("license_key", client.getLicense_key()), client);
            LOGGER.log(Level.INFO, "License: [{0}] expired.", client.getLicense_key());

            // try to allocate a server to valid clients.
            ServerRepository.allocateServersToClients();
            Thread.currentThread().interrupt();
        }
    }
}
