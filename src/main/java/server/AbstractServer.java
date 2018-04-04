package server;


import model.Artist;
import model.Concert;
import model.Location;
import model.Ticket;
import networking.IObserver;
import service.ConcertService;
import service.LoginService;
import service.Service;
import sun.rmi.runtime.Log;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 11:41:16 AM
 */
public abstract class AbstractServer {
    private int port;
    protected ConcertService concertService;

    protected Service<Integer, Artist> artistService;

    protected Service<Integer, Location> locationService;

    protected Service<Integer, Ticket> ticketService;

    protected Map<String, Integer> clients; //username + port

    public void setTicketService(Service<Integer, Ticket> ticketService) {
        this.ticketService = ticketService;
    }

    public void setConcertService(ConcertService concertService) {
        this.concertService = concertService;
    }

    public void setArtistService(Service<Integer, Artist> artistService) {
        this.artistService = artistService;
    }

    public void setLocationService(Service<Integer, Location> locationService) {
        this.locationService = locationService;
    }

    protected LoginService loginService;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    private ServerSocket server = null;

    public AbstractServer(int port) {
        this.port = port;
        this.clients=new ConcurrentHashMap<>();
    }

    public void start() throws ServerException {
        try {
            server = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for clients ...");
                Socket client = server.accept();
                System.out.println("Client connected ...");
                processRequest(client);
            }
        } catch (IOException e) {
            throw new ServerException("Starting server errror ", e);
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                throw new ServerException("Closing server error ", e);
            }
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop() throws ServerException {
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}
