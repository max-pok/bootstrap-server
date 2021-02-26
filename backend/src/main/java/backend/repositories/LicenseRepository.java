package backend.repositories;

import backend.models.Client;
import backend.models.License;
import backend.models.Request;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.model.Filters;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class LicenseRepository {

    private final static Logger LOGGER = Logger.getLogger(LicenseRepository.class.getName());

    /**
     * Checks if the request that made has an available licence key
     */
    public void requestVerification(Request request) {
        License license = this.getLicense(request.license_key);
        //  check if license_key not exists in licenses collection
        if (license == null) {
            LOGGER.log(Level.WARNING, "No such license key [license: " + request.getLicense_key() + "]");
            return;
        }

        // check if license_key is not connected to client_id in licences collection => update license owner
        if (license.getClient_id() == null) {
            license.setClient_id(request.customer_id);
            this.updateLicense(license);
            LOGGER.log(Level.INFO,  "Client id updated [license: " + request.getLicense_key() + "]");
        }
    }

    public List<String> getUnusedLicenses() {
        DistinctIterable<String> licenses = MongoDB.licensesCollection.distinct("license_key", Filters.eq("client_id", null), String.class);
        List<String> licensesList = new ArrayList<>();
        for (String license : licenses) {
            licensesList.add(license);
        }
        return licensesList;
    }

    public License getLicense(String license_key) {
        return MongoDB.licensesCollection.find(Filters.eq("license_key", license_key)).first();
    }

    public void updateLicense(License license) {
        MongoDB.licensesCollection.replaceOne(Filters.eq("license_key", license.getLicense_key()), license);
    }
}