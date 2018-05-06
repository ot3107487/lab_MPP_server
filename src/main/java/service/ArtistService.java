package service;

import model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ArtistRepositoryJPA;

import java.util.ArrayList;

@Service
public class ArtistService implements IService<Artist> {

    private final ArtistRepositoryJPA artistRepositoryJPA;

    @Autowired
    public ArtistService(ArtistRepositoryJPA artistRepositoryJPA) {
        this.artistRepositoryJPA = artistRepositoryJPA;
    }

    public void save(Artist elem) {
        artistRepositoryJPA.save(elem);
    }

    public int size() {
        return (int) artistRepositoryJPA.count();
    }


    public void delete(Artist elem) {
        artistRepositoryJPA.delete(elem);
    }

    public void put(Artist elem) {
        artistRepositoryJPA.save(elem);

    }

    public ArrayList<Artist> getAll() {
        return (ArrayList<Artist>) artistRepositoryJPA.findAll();
    }

    public Artist findById(int id) {
        return artistRepositoryJPA.findOne(id);
    }
}
