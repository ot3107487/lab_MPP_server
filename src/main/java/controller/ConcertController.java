package controller;

import model.Artist;
import model.Concert;
import model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.interfaces.IArtistService;
import service.interfaces.IConcertService;
import service.interfaces.ILocationService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/concerts")
public class ConcertController {
    private final IConcertService concertService;
    private final IArtistService artstService;

    private final ILocationService locationService;

    @Autowired
    public ConcertController(IConcertService concertService, IArtistService artstService, ILocationService locationService) {
        this.concertService = concertService;
        this.artstService = artstService;
        this.locationService = locationService;
    }

    @GetMapping
    public ArrayList<Concert> getConcertsByArtistAndDate(@RequestParam(value = "idArtist", required = false) String idArtist,
                                                         @RequestParam(value = "date", required = false) String date,
                                                         HttpServletResponse response) {
        if (idArtist == null)
            if (date == null)
                return concertService.getAll();
            else
                return concertService.getConcertsByDate(date);

        else {
            Artist artist = artstService.findById(Integer.parseInt(idArtist));
            if (artist == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            } else {
                if (date == null)
                    return concertService.getConcertsByArtist(artist);
                else
                    return concertService.getConcertsByArtistAndDate(artist, date);
            }
        }
    }

    @PostMapping
    public void saveConcert(@RequestBody Concert concert, @RequestHeader("idArtist") String idArtist,
                            @RequestHeader("idLocation") String idLocation) {
        Artist artist = artstService.findById(Integer.parseInt(idArtist));
        Location location = locationService.findById(Integer.parseInt(idLocation));
        concert.setArtist(artist);
        concert.setLocation(location);
        concertService.save(concert);
    }

    @PutMapping(value = "/{idConcert}")
    public void updateConcert(@RequestBody Concert concert, @PathVariable("idConcert") int id, HttpServletResponse response,
                              @RequestHeader("idArtist") String idArtist, @RequestHeader("idLocation") String idLocation) {
        Concert dbConcert = concertService.findById(id);
        if (dbConcert == null)
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        else {
            Artist artist = artstService.findById(Integer.parseInt(idArtist));
            Location location = locationService.findById(Integer.parseInt(idLocation));
            concert.setArtist(artist);
            concert.setLocation(location);
            concertService.put(concert);
        }
    }
}

