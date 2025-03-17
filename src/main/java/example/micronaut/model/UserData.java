package example.micronaut.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class UserData {
    private String name;
    private int id;
    private String location;

    public UserData(String name, int id, String location) {
        this.name = name;
        this.id = id;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }
}


