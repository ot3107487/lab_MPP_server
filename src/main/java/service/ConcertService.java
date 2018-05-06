package service;

import model.Artist;
import model.Concert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ConcertRepositoryJPA;

import java.util.ArrayList;

@Service
public class ConcertService implements IConcertService{
    private final ConcertRepositoryJPA concertRepositoryJPA;

    @Autowired
    public ConcertService(ConcertRepositoryJPA concertRepositoryJPA) {
        this.concertRepositoryJPA = concertRepositoryJPA;
    }

    @Override
    public ArrayList<Concert> getConcertsByArtist(Artist artist) {
        return concertRepositoryJPA.getAllByArtist(artist);
    }

    @Override
    public ArrayList<Concert> getConcertsByDate(String date) {
        return concertRepositoryJPA.getAllByDate(date);
    }

    @Override
    public ArrayList<Concert> getConcertsByArtistAndDate(Artist artist, String date) {
        return concertRepositoryJPA.getAllByArtistAndDate(artist,date);
    }

    @Override
    public void save(Concert elem) {
        concertRepositoryJPA.save(elem);

    }

    @Override
    public int size() {
        return (int)concertRepositoryJPA.count();
    }

    @Override
    public void delete(Concert elem) {
        concertRepositoryJPA.delete(elem.getId());
    }

    @Override
    public void put(Concert elem) {
        concertRepositoryJPA.save(elem);
    }

    @Override
    public ArrayList<Concert> getAll() {
        return (ArrayList<Concert>) concertRepositoryJPA.findAll();
    }
}
