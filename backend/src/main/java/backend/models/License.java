package backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String license_id;
    private String client_id;
    private String license_key;
    private long license_expiration_time;

    public License() {}

    public License(String license_id, String client_id, String license_key, long license_expiration_time) {
        this.license_id = license_id;
        this.client_id = client_id;
        this.license_key = license_key;
        this.license_expiration_time = license_expiration_time;
    }

    public String getLicense_id() {
        return license_id;
    }

    public void setLicense_id(String license_id) {
        this.license_id = license_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getLicense_key() {
        return license_key;
    }

    public void setLicense_key(String license_key) {
        this.license_key = license_key;
    }

    @Override
    public String toString() {
        return "License{" +
                "license_id='" + license_id + '\'' +
                ", client_id='" + client_id + '\'' +
                ", license_key='" + license_key + '\'' +
                ", license_expiration_time=" + license_expiration_time +
                '}';
    }

    public long getLicense_expiration_time() {
        return license_expiration_time;
    }

    public void setLicense_expiration_time(long license_expiration_time) {
        this.license_expiration_time = license_expiration_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        License license = (License) o;
        return license_expiration_time == license.license_expiration_time && license_id.equals(license.license_id) && Objects.equals(client_id, license.client_id) && license_key.equals(license.license_key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(license_id, client_id, license_key, license_expiration_time);
    }
}
