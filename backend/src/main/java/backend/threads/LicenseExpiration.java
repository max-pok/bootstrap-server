package backend.threads;

import backend.models.Client;
import backend.repositories.MongoDB;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LicenseExpiration implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(LicenseExpiration.class.getName());

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
                LOGGER.log(Level.INFO, "Thread finished.");
            }
        }
    }

    public synchronized void updateExpirationTime() {
        client.setLicense_expiration_time(client.getLicense_expiration_time() - 1);
        MongoDB.clientsCollection.replaceOne(Filters.eq("license_key", client.getLicense_key()), client);
        if (client.getLicense_expiration_time() == 0) {
            client.setServer_id("");
            client.setClients_capacity(0);
            MongoDB.clientsCollection.replaceOne(Filters.eq("license_key", client.getLicense_key()), client, new ReplaceOptions().upsert(true));
            LOGGER.log(Level.INFO, "License: [{0}] expired.", client.getLicense_key());
            Thread.currentThread().interrupt();
        }
    }
}
