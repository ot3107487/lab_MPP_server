package repository;

import model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepositoryJPA extends JpaRepository<Artist,Integer> {
}
