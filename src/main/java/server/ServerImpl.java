package server;

import model.Artist;
import model.Concert;
import model.Location;
import model.Ticket;
import networking.IObserver;
import networking.IServer;
import service.ConcertService;
import service.LoginService;
import service.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerImpl implements IServer {
    private final LoginService loginService;
    private final Service<Integer, Artist> artistService;
    private final Service<Integer, Location> locationService;
    private final ConcertService concertService;
    private final Service<Integer, Ticket> ticketService;
    private ArrayList<IObserver> clients;

    public ServerImpl(LoginService loginService, Service<Integer, Artist> artistService, Service<Integer, Location> locationService, ConcertService concertService, Service<Integer, Ticket> ticketService) {

        this.loginService = loginService;
        this.artistService = artistService;
        this.locationService = locationService;
        this.concertService = concertService;
        this.ticketService = ticketService;
        this.clients = new ArrayList<>();
    }

    public boolean login(String userName, String password, IObserver client) throws RemoteException {
        if (loginService.login(userName, password)) {
            clients.add(client);
            return true;
        }
        return false;
    }

    public ArrayList<Artist> getArtists() throws RemoteException {
        return artistService.getAll();
    }

    public ArrayList<Concert> getConcerts() throws RemoteException {
        return concertService.getAll();
    }

    public ArrayList<Location> getLocation() throws RemoteException {
        return locationService.getAll();
    }

    public ArrayList<Concert> getConcertsByArtist(Artist artist) throws RemoteException {
        return this.concertService.getConcertsByArtist(artist);
    }

    public ArrayList<Concert> getConcertsByDate(String date) throws RemoteException {
        return this.concertService.getConcertsByDate(date);
    }

    public ArrayList<Concert> getConcertsByArtistAndDate(Artist artist, String date) throws RemoteException {
        return this.concertService.getConcertsByArtistAndDate(artist, date);

    }

    public void saveTicket(Ticket ticket) throws RemoteException {
        this.ticketService.save(ticket);
    }

    public void updateConcert(Concert concert) throws RemoteException {
        this.concertService.put(concert);
        for (IObserver client : clients) {
            client.concertUpdated(concert);
        }
    }

    @Override
    public void logout(IObserver client) throws RemoteException {
        this.clients.remove(client);
    }


}
