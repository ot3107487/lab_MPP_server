package repository;

import model.HasId;

public interface Repository<Id, T extends HasId<Id>> {
    void save(T elem);

    int size();

    void delete(T elem);

    void put(T elem);

    Iterable<T> getAll();

    T findById(Id id);
}
