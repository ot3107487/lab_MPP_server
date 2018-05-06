package controller;

import model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.interfaces.ILocationService;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/locations")
public class LocationController {
    private final ILocationService locationService;

    @Autowired
    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ArrayList<Location> getLocations() {
        return locationService.getAll();
    }

    @PostMapping
    public void saveLocation(@RequestBody Location location){
        locationService.save(location);
    }
}
