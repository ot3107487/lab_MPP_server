package model;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Artist implements Serializable,HasId<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="id")
    private int id;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    public Artist() {
    }

    public Artist(int id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        return id == artist.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
