package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Location implements HasId<Integer>,Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "places")
    private int places;

    @OneToMany(mappedBy = "location")
    private List<Concert> concerts;


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
