package service;

import model.HasId;
import repository.Repository;

import java.util.ArrayList;

public class Service<Id, T extends HasId<Id>> {
    protected Repository<Id, T> repository;

    public Service(Repository<Id, T> repository) {
        this.repository = repository;
    }

    public void save(T elem) {
        repository.save(elem);
    }

    public int size() {
        return repository.size();
    }


    public void delete(T elem) {
        repository.delete(elem);
    }

    public void put(T elem) {
        repository.put(elem);

    }

    public ArrayList<T> getAll() {
        return (ArrayList<T>) repository.getAll();
    }

    public T findById(Id id) {
        return repository.findById(id);
    }
}