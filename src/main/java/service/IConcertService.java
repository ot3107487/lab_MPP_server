package service;

import model.Artist;
import model.Concert;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IConcertService extends IService<Concert> {
    ArrayList<Concert> getConcertsByArtist(Artist artist);

    ArrayList<Concert> getConcertsByDate(String date);

    ArrayList<Concert> getConcertsByArtistAndDate(Artist artist, String date);
}