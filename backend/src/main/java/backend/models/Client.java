package backend.models;

import javax.persistence.Entity;
import java.util.Objects;

public class Client {
    private String client_id;
    private String license_key;
    private long license_expiration_time;
    private String server_id;
    private long clients_capacity;
    private String location;

    public Client() {}

    public Client(String client_id, String license_key, long license_expiration_time, String server_id, long clients_capacity, String location) {
        this.client_id = client_id;
        this.license_key = license_key;
        this.license_expiration_time = license_expiration_time;
        this.server_id = server_id;
        this.clients_capacity = clients_capacity;
        this.location = location;
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

    public long getLicense_expiration_time() {
        return license_expiration_time;
    }

    public void setLicense_expiration_time(long license_expiration_time) {
        this.license_expiration_time = license_expiration_time;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public long getClients_capacity() {
        return clients_capacity;
    }

    public void setClients_capacity(long clients_capacity) {
        this.clients_capacity = clients_capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Client{" +
                "client_id='" + client_id + '\'' +
                ", license_key='" + license_key + '\'' +
                ", license_expiration_time=" + license_expiration_time +
                ", server_id='" + server_id + '\'' +
                ", clients_capacity=" + clients_capacity +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return license_expiration_time == client.license_expiration_time && clients_capacity == client.clients_capacity && client_id.equals(client.client_id) && license_key.equals(client.license_key) && Objects.equals(server_id, client.server_id) && location.equals(client.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client_id, license_key, license_expiration_time, server_id, clients_capacity, location);
    }
}
