package model;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Location implements HasId<Integer>,Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "places")
    private int places;

    public Location() {
    }

    public Location(int id, String name, int places) {
        this.id = id;
        this.name = name;
        this.places = places;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }
}
