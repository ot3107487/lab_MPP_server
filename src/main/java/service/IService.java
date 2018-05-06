package service;

import java.util.ArrayList;

public interface IService<T> {

    void save(T elem);

    int size();


    void delete(T elem);

    void put(T elem);

    ArrayList<T> getAll();
}
