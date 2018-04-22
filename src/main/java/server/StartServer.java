package server;


import model.Artist;
import model.Location;
import model.Ticket;
import networking.IServer;
import repository.*;
import repository_utils.PropertiesForJDBC;
import service.ConcertService;
import service.LoginService;
import service.Service;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

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

        Properties serverProps=new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        System.setProperty("java.security.policy","file:server.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        IServer serverImpl=new ServerImpl(loginService,artistService,locationService,concertService,ticketService);

        try {
            String name = serverProps.getProperty("rmi.serverID","Server");
            IServer stub =(IServer) UnicastRemoteObject.exportObject(serverImpl, 0);
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("before binding");
            registry.rebind(name, stub);
            System.out.println("Chat server   bound");
        } catch (Exception e) {
            System.err.println("Server exception:"+e);
            e.printStackTrace();
        }

//        AbstractServer server = new SerialConcurrentServer(55555);
//        server.setLoginService(loginService);
//        server.setArtistService(artistService);
//        server.setLocationService(locationService);
//        server.setConcertService(concertService);
//        server.setTicketService(ticketService);
//
//        try {
//            server.start();
//        } catch (ServerException e) {
//            System.out.println(e.getMessage());
//        }
    }
}
