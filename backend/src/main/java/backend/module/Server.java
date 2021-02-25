package backend.module;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String server_id;
    private String server_ip_address;
    private int clients_capacity;
    private String location;

    public Server() {}

    public Server(String server_id, String server_ip_address, int clients_capacity, String location) {
        this.server_id = server_id;
        this.server_ip_address = server_ip_address;
        this.clients_capacity = clients_capacity;
        this.location = location;
    }

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
}
