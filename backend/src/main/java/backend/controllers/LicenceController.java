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

    @GetMapping(value = "/response", produces="text/plain")
    @ResponseBody
    private String getResponse(@RequestParam("customer_id") String customer_id, @RequestParam("license_key") String license_key) {
        License license = this.licenseRepository.getLicense(license_key);
        if (license == null) {
            System.out.println("no such licence");
            return "\"" + "NO SUCH LICENCE" + "\"";
        }

        if (license.getClient_id() == null) {
            return "\"" + "LICENCE KEY UNOCCUPIED" + "\"";
        }

        if (license.getClient_id().equals(customer_id)) {
            return "\"" + "LICENCE KEY REQUEST ACCEPTED" + "\"";
        }

        else if (license.getLicense_expiration_time()  == 0) {
            return "\"" + "LICENCE KEY EXPIRED" + "\"";
        }

        else if (!license.getClient_id().equals(customer_id)) {
            return "\"" + "LICENCE KEY ALREADY OCCUPIED" + "\"";
        }

        return "\"" + "" + "\"";
    }
}
