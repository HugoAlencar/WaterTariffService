package br.com.hraa.watertariffservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "client_category")
public class ClientCategory {
    @Id
    private int id;

    @Column(nullable = false)
    private String name;

    protected ClientCategory() { }

    public ClientCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("ClientCategory[id=%d, name=%s]", id, name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
