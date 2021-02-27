package backend.repositories;

import backend.models.License;
import backend.models.Request;
import com.mongodb.client.model.Filters;
import org.springframework.stereotype.Repository;
import backend.utilities.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class LicenseRepository {

    private static final Logger LOGGER = Logger.getLogger(LicenseRepository.class.getName());
    public final ClientRepository clientRepository;
    public static final String LICENSE_KEY = "license_key";

    public LicenseRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Checks if the request that made has an available licence key.
     */
    public void requestVerification(Request request) {
        License license = this.getLicense(request.license_key);
        //  check if license_key not exists in licenses collection
        if (license == null) {
            LOGGER.log(Level.WARNING, "No such license key [license: {0}]", request.getLicense_key());
            return;
        }

        // check if license_key is not connected to client_id in licences collection => update license owner
        if (license.getClient_id() == null) {
            license.setClient_id(request.customer_id);
            this.updateLicense(license);
            LOGGER.log(Level.INFO,  "Client id updated [license: {0}]", request.getLicense_key());

            // add the client to clients collection.
            this.clientRepository.initClientInformation(license, request.location);
        }
    }

    /**
     * @returns all the available license keys from the licenses collection.
     */
    public List<String> getUnusedLicenses() {
        return MongoDB.licensesCollection.distinct(LICENSE_KEY, Filters.eq("client_id", null), String.class).into(new ArrayList<>());
    }

    /**
     * @returns License class based on a given license key.
     */
    public License getLicense(String license_key) {
        return MongoDB.licensesCollection.find(Filters.eq(LICENSE_KEY, license_key)).first();
    }

    /**
     * Updates the license data.
     */
    public void updateLicense(License license) {
        MongoDB.licensesCollection.replaceOne(Filters.eq(LICENSE_KEY, license.getLicense_key()), license);
    }
}
