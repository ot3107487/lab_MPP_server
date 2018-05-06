package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Ticket implements HasId<Integer>,Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConcert", nullable = false)
    @JsonIgnore
    private Concert concert;

    @Column(name = "placesBought")
    private int placesBought;

    @Column(name="buyer")
    private String buyer;

    public Ticket() {
    }

    public Ticket(Concert concert, int placesBought, String buyer) {
        this.concert = concert;
        this.placesBought = placesBought;
        this.buyer = buyer;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    public int getPlacesBought() {
        return placesBought;
    }

    public void setPlacesBought(int placesBought) {
        this.placesBought = placesBought;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
}
