package backend.threads;

import backend.models.Client;
import backend.repositories.MongoDB;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LicenseExpiration implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(LicenseExpiration.class.getName());

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000);
                updateExpirationTime();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void updateExpirationTime() {
        List<Client> clientList = MongoDB.clientsCollection.find(
                Filters.and(Filters.ne("server_id", ""), Filters.ne("license_expiration_time", 0))).into(new ArrayList<>());

        for (Client client: clientList) {
            client.setLicense_expiration_time(client.getLicense_expiration_time() - 1);
            MongoDB.clientsCollection.replaceOne(Filters.eq("license_key", client.getLicense_key()), client);
            if (client.getLicense_expiration_time() == 0) {
                client.setServer_id("");
                System.out.println("s: " + client.getServer_id());
                client.setClients_capacity(0);
                MongoDB.clientsCollection.replaceOne(Filters.eq("license_key", client.getLicense_key()), client, new ReplaceOptions().upsert(true));
                LOGGER.log(Level.INFO, "License: [" + client.getLicense_key() + "] expired.");
            }
        }
    }
}
