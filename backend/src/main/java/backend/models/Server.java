package backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String server_id;
    private String server_ip_address;
    private int clients_capacity;
    private int current_clients_capacity;
    private String location;

    public Server(String server_id, String server_ip_address, int clients_capacity, int current_clients_capacity, String location) {
        this.server_id = server_id;
        this.server_ip_address = server_ip_address;
        this.clients_capacity = clients_capacity;
        this.current_clients_capacity = current_clients_capacity;
        this.location = location;
    }

    public Server() {}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getClients_capacity() {
        return clients_capacity;
    }

    public void setClients_capacity(int clients_capacity) {
        this.clients_capacity = clients_capacity;
    }

    public String getServer_ip_address() {
        return server_ip_address;
    }

    public void setServer_ip_address(String server_ip_address) {
        this.server_ip_address = server_ip_address;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public int getCurrent_clients_capacity() {
        return current_clients_capacity;
    }

    public void setCurrent_clients_capacity(int current_clients_capacity) {
        this.current_clients_capacity = current_clients_capacity;
    }

    @Override
    public String toString() {
        return "Server{" +
                "server_id='" + server_id + '\'' +
                ", server_ip_address='" + server_ip_address + '\'' +
                ", clients_capacity=" + clients_capacity +
                ", current_clients_capacity=" + current_clients_capacity +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return clients_capacity == server.clients_capacity && current_clients_capacity == server.current_clients_capacity && server_id.equals(server.server_id) && server_ip_address.equals(server.server_ip_address) && location.equals(server.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server_id, server_ip_address, clients_capacity, current_clients_capacity, location);
    }
}
