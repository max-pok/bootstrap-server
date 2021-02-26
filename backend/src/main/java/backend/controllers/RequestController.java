package backend.controllers;

import backend.models.Request;
import backend.repositories.ClientRepository;
import backend.repositories.LicenseRepository;
import backend.repositories.RequestRepository;
import backend.repositories.ServerRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RequestController {

    private final RequestRepository requestRepository;
    private final LicenseRepository licenseRepository;
    private final ServerRepository serverRepository;

    public RequestController(RequestRepository requestRepository, LicenseRepository licenseRepository,
                             ServerRepository serverRepository) {
        this.requestRepository = requestRepository;
        this.licenseRepository = licenseRepository;
        this.serverRepository = serverRepository;
    }

    /**
     * Adds request to database
     */
    @PostMapping("/request")
    public void addRequest(@RequestBody Request request) {
        // if the same request exist => replace it, else => add it.
        this.requestRepository.saveRequest(request);

        // if there is available licence key for the request
        this.licenseRepository.requestVerification(request);
    }

    @GetMapping("/request")
    private Map<String, List<String>> getLocationsAndLicences() {
        // Get locations
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
