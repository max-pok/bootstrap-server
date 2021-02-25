package backend.controller;

import backend.module.Request;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RequestController {

    public RequestController() {}

    /**
     * Adds request to database
     */
    @PostMapping("/request")
    public void addRequest(@RequestBody Request request) {
        BasicDBObject document = new BasicDBObject();
        document.put("customer_id", request.customer_id);
        document.put("location", request.location);
        document.put("license_key", request.license_key);
        // if the same request exist => replace it, else => add it.
        MongoDB.requestsCollection.replaceOne(Filters.and(Filters.eq("customer_id", request.customer_id),
                Filters.eq("location", request.location),
                Filters.eq("license_key", request.license_key)), document, new ReplaceOptions().upsert(true));

        // check if there is available licence key for the request
         requestVerification(document);
    }

    /**
     * Checks if the request that made has an available licence key
     */
    public void requestVerification(BasicDBObject document) {
        BasicDBObject myDoc = MongoDB.licensesCollection.find(Filters.eq("license_key", document.get("license_key"))).first();
        //  check if license_key not exists in licenses collection
        if (myDoc == null) {
            System.out.println("No such license key.");
            return;
        }

        // check if license_key is connected to client_id in licences collection
        if (myDoc.get("client_id") != null) {
            System.out.println("License key is occupied.");
        } else {
            // else if not connected to client_id => update licences collection with document.customer_id
            myDoc.replace("client_id", document.get("customer_id"));
            MongoDB.licensesCollection.replaceOne(Filters.eq("license_key", document.get("license_key")), myDoc);
            System.out.println("updated.");
        }
    }

    public void removeRequest(BasicDBObject document) {
        if (MongoDB.requestsCollection.deleteOne(document).wasAcknowledged()) {
            System.out.println("request deleted");
        } else {
            System.out.println("request not deleted");
        }
    }

    @GetMapping("/request")
    private Map<String, List<String>> getLocationsAndLicences() {
        // Get locations
        DistinctIterable<String> locations = MongoDB.serversCollection.distinct("location", String.class);
        List<String> locationsList = new ArrayList<>();
        for (String location : locations) {
            locationsList.add(location);
        }

        // Get unused licences
        DistinctIterable<String> licenses = MongoDB.licensesCollection.distinct("license_key", Filters.eq("client_id", null), String.class);
        List<String> licensesList = new ArrayList<>();
        for (String license : licenses) {
            licensesList.add(license);
        }

        // Combine locations & licences
        Map<String, List<String>> map = new HashMap<>();
        map.put("locations", locationsList);
        map.put("licences", licensesList);
        return map;
    }

    @RequestMapping(value = "/response", method = RequestMethod.GET, produces="text/plain")
    @ResponseBody
    private String getResponse(@RequestParam("customer_id") String customer_id, @RequestParam("license_key") String license_key) {
        BasicDBObject licenses = MongoDB.licensesCollection.find(Filters.eq("license_key", license_key)).first();
        if (licenses == null) {
            System.out.println("no such licence");
            return "\"" + "NO SUCH LICENCE" + "\"";
        }

        if (licenses.get("client_id") == null) {
            return "\"" + "LICENCE KEY UNOCCUPIED" + "\"";
        }

        if (licenses.get("client_id").toString().equals(customer_id)) {
            return "\"" + "LICENCE KEY REQUEST ACCEPTED" + "\"";
        }

        else if (licenses.get("license_expiration_time").toString().equals("0")) {
            return "\"" + "LICENCE KEY EXPIRED" + "\"";
        }

        else if (!licenses.get("client_id").toString().equals(customer_id)) {
            return "\"" + "LICENCE KEY ALREADY OCCUPIED" + "\"";
        }

        return "\"" + "" + "\"";
    }
}
