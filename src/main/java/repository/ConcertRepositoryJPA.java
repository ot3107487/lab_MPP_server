package repository;

import model.Artist;
import model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ConcertRepositoryJPA extends JpaRepository<Concert,Integer> {
    ArrayList<Concert> getAllByArtist(Artist artist);
    ArrayList<Concert> getAllByDate(String date);
    ArrayList<Concert> getAllByArtistAndDate(Artist artist, String date);

}
