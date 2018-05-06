package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.websocket.ClientEndpoint;
import java.io.Serializable;

@Entity
public class Concert implements HasId<Integer>,Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idArtist", nullable = false)
    @JsonIgnore
    private Artist artist;

    @Column(name="date")
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idLocation", nullable = false)
    @JsonIgnore
    private Location location;

    @Column(name="numberOfTickets")
    private int numberOfTickets;

    @Column(name="soldTickets")
    private int soldTickets;

    public Concert() {
    }

    public Concert(Artist artist, String date, Location location, int numberOfTickets, int soldTickets) {
        this.artist = artist;
        this.date = date;
        this.location = location;
        this.numberOfTickets = numberOfTickets;
        this.soldTickets = soldTickets;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }
}
