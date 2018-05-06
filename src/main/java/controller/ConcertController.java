package controller;

import model.Concert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.IConcertService;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/concerts")
public class ConcertController {
    private final IConcertService concertService;

    @Autowired
    public ConcertController(IConcertService concertService) {
        this.concertService = concertService;
    }

    @GetMapping
    public ArrayList<Concert> getAll(){
        return concertService.getAll();
    }


}
