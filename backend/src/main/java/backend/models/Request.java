package backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String customer_id;
    public String location;
    public String license_key;

    public Request() {}

    public Request(String customer_id, String location, String license_key) {
        this.customer_id = customer_id;
        this.location = location;
        this.license_key = license_key;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLicense_key() {
        return license_key;
    }

    public void setLicense_key(String license_key) {
        this.license_key = license_key;
    }

    @Override
    public String toString() {
        return "Request{" +
                "customer_id='" + customer_id + '\'' +
                ", location='" + location + '\'' +
                ", license_key='" + license_key + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return customer_id.equals(request.customer_id) && location.equals(request.location) && license_key.equals(request.license_key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer_id, location, license_key);
    }
}
