package controller;

import model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.interfaces.IArtistService;

import javax.servlet.http.HttpServletResponse;
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
    public void saveArtist(@RequestBody Artist artist) {
        artistService.save(artist);
    }

    @GetMapping(value = "/{idArtist}")
    public Artist findById(@PathVariable("idArtist") String idArtist, HttpServletResponse response) {
        Artist artist = artistService.findById(Integer.parseInt(idArtist));
        if (artist == null)
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return artist;
    }

    @PutMapping
    public void put(@RequestBody Artist artist) {
        artistService.put(artist);
    }

    @DeleteMapping(value = "/{idArtist}")
    public void delete(@PathVariable("idArtist") String idArtist,HttpServletResponse response) {
        Artist artist=artistService.findById(Integer.parseInt(idArtist));
        artistService.delete(artist);
        Artist deletedArtist=artistService.findById(Integer.parseInt(idArtist));
        if(deletedArtist != null)
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
