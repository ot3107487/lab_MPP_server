package controller;

import model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import service.interfaces.IArtistService;
import service.interfaces.IService;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController {
    private final IArtistService artistService;

    @Autowired
    public ArtistController(IArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ArrayList<Artist> getArtists() {
        return artistService.getAll();
    }

    @PostMapping
    public void saveArtist(@RequestBody Artist artist){
        artistService.save(artist);
    }
}
