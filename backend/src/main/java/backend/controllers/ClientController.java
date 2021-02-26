package backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DataController {
    public DataController() {}

    /**
     * For each client with a licence without a server
     * Check if there is a available server for him.
     */
    public void licensesVerificationWithServer() {

    }



}
