package backend.controllers;

import backend.models.Request;
import backend.repositories.LicenseRepository;
import backend.repositories.ServerRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RequestController {

    private final LicenseRepository licenseRepository;
    private final ServerRepository serverRepository;

    public RequestController(LicenseRepository licenseRepository,
                             ServerRepository serverRepository) {
        this.licenseRepository = licenseRepository;
        this.serverRepository = serverRepository;
    }

    /**
     * Client request submit.
     */
    @PostMapping("/request")
    public void addRequest(@RequestBody Request request) {
        // checks if there is available licence key for the request
        this.licenseRepository.requestVerification(request);
    }


    /**
     * @returns distict locations and unused licences for the request form in the frontend.
     */
    @GetMapping("/request")
    private Map<String, List<String>> getLocationsAndLicences() {
        // Get distict locations
        List<String> locationsList = this.serverRepository.getDistinctLocations();

        // Get unused licences
        List<String> licensesList = this.licenseRepository.getUnusedLicenses();

        // Combine locations & licences
        Map<String, List<String>> map = new HashMap<>();
        map.put("locations", locationsList);
        map.put("licences", licensesList);
        return map;
    }
}
