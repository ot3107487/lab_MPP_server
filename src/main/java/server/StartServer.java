package server;


import model.Artist;
import model.Location;
import model.Ticket;
import repository.*;
import repository_utils.PropertiesForJDBC;
import service.ConcertService;
import service.LoginService;
import service.Service;

public class StartServer {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository(PropertiesForJDBC.getProperties());
        ArtistRepository artistRepository=new ArtistRepository(PropertiesForJDBC.getProperties());
        ConcertRepository concertRepository=new ConcertRepository(PropertiesForJDBC.getProperties());
        LocationRepository locationRepository=new LocationRepository(PropertiesForJDBC.getProperties());
        TicketRepository ticketRepository=new TicketRepository(PropertiesForJDBC.getProperties());

        LoginService loginService = new LoginService(userRepository);

        Service<Integer, Artist> artistService=new Service<>(artistRepository);
        ConcertService concertService=new ConcertService(concertRepository);
        Service<Integer,Location> locationService=new Service<>(locationRepository);
        Service<Integer, Ticket> ticketService=new Service<>(ticketRepository);

        AbstractServer server = new SerialConcurrentServer(55555);
        server.setLoginService(loginService);
        server.setArtistService(artistService);
        server.setLocationService(locationService);
        server.setConcertService(concertService);
        server.setTicketService(ticketService);

        try {
            server.start();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}
