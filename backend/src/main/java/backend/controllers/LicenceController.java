package backend.controllers;

import backend.models.License;
import backend.repositories.LicenseRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LicenceController {

    private final LicenseRepository licenseRepository;

    public LicenceController(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    /**
     * After request been made
     * @return the frontend (the client) a response about his last request submission.
     */
    @GetMapping(value = "/request/{id}/{key}/response", produces="text/plain")
    private String getResponseFromRequest(@PathVariable("id") String customer_id, @PathVariable("key") String license_key) {
        License license = this.licenseRepository.getLicense(license_key);
        
        if (license == null) {
            return "\"" + "No Such license." + "\"";
        }

        if (license.getClient_id() == null) {
            return "\"" + "License key is unoccupied." + "\"";
        }

        if (license.getClient_id().equals(customer_id)) {
            return "\"" + "License key request accepted." + "\"";
        }

        else if (license.getLicense_expiration_time()  == 0) {
            return "\"" + "License key expired." + "\"";
        }

        else if (!license.getClient_id().equals(customer_id)) {
            return "\"" + "License key is already occupied." + "\"";
        }

        return "\"" + "" + "\"";
    }
}
