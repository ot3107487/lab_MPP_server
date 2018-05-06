package service;

import model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.LocationRepositoryJPA;

import java.util.ArrayList;

@Service
public class LocationService implements IService<Location> {
    private final LocationRepositoryJPA locationRepositoryJPA;

    @Autowired
    public LocationService(LocationRepositoryJPA locationRepositoryJPA) {
        this.locationRepositoryJPA = locationRepositoryJPA;
    }

    @Override
    public void save(Location elem) {
        locationRepositoryJPA.save(elem);
    }

    @Override
    public int size() {
        return (int) locationRepositoryJPA.count();
    }

    @Override
    public void delete(Location elem) {
        locationRepositoryJPA.delete(elem.getId());
    }

    @Override
    public void put(Location elem) {
        locationRepositoryJPA.save(elem);
    }

    @Override
    public ArrayList<Location> getAll() {
        return (ArrayList<Location>) locationRepositoryJPA.findAll();
    }
}
