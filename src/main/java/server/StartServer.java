package server;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import model.Artist;
import model.Location;
import model.Ticket;
import repository.*;
import repository_utils.PropertiesForJDBC;
import service.ConcertService;
import service.LoginService;
import service.Service;

import java.io.IOException;

public class StartServer {

    private Server server;

    private void start() throws IOException {
    /* The port on which the server should run */
        UserRepository userRepository = new UserRepository(PropertiesForJDBC.getProperties());
        ArtistRepository artistRepository = new ArtistRepository(PropertiesForJDBC.getProperties());
        ConcertRepository concertRepository = new ConcertRepository(PropertiesForJDBC.getProperties());
        LocationRepository locationRepository = new LocationRepository(PropertiesForJDBC.getProperties());
        TicketRepository ticketRepository = new TicketRepository(PropertiesForJDBC.getProperties());

        LoginService loginService = new LoginService(userRepository);

        Service<Integer, Artist> artistService = new Service<>(artistRepository);
        ConcertService concertService = new ConcertService(concertRepository);
        Service<Integer, Location> locationService = new Service<>(locationRepository);
        Service<Integer, Ticket> ticketService = new Service<>(ticketRepository);

        ServerGrpcImpl serverImpl = new ServerGrpcImpl(loginService, artistService, locationService, concertService, ticketService);


        int port = 55555;
        server = ServerBuilder.forPort(port)
                .addService(serverImpl)
                .build()
                .start();
        //logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                StartServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */


    public static void main(String[] args) {
        final StartServer server = new StartServer();
        try {
            System.out.println("Starting server...");
            server.start();
            System.out.println("Server started!");
            server.blockUntilShutdown();
        } catch (Exception e) {
            System.err.println("Server exception:" + e);
            e.printStackTrace();
        }

    }
}
