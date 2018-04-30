package server;

import generatedFiles.services.Request;
import generatedFiles.services.RequestType;
import generatedFiles.services.Response;
import generatedFiles.services.ServerImplementationGrpc;
import io.grpc.stub.StreamObserver;
import model.Artist;
import model.Location;
import model.Ticket;
import service.ConcertService;
import service.LoginService;
import service.Service;

public class ServerGrpcImpl extends ServerImplementationGrpc.ServerImplementationImplBase {
    private final LoginService loginService;
    private final Service<Integer, Artist> artistService;
    private final Service<Integer, Location> locationService;
    private final ConcertService concertService;
    private final Service<Integer, Ticket> ticketService;

    public ServerGrpcImpl(LoginService loginService, Service<Integer, Artist> artistService, Service<Integer, Location> locationService, ConcertService concertService, Service<Integer, Ticket> ticketService) {

        this.loginService = loginService;
        this.artistService = artistService;
        this.locationService = locationService;
        this.concertService = concertService;
        this.ticketService = ticketService;
    }

    @Override
    public void clientRequest(Request request, StreamObserver<Response> responseObserver) {
        if (request.getRequestType().equals(RequestType.LOGIN)) {
            String body = request.getBodyAsString();
            String[] credentials = body.split(" ");
            String userName = credentials[0];
            String password = credentials[1];
            System.out.println("Sending response");
            String status = Boolean.toString(loginService.login(userName, password));
            Response response = Response.newBuilder().setStatus(status).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }
//            if (request.getRequestType().equals(RequestType.UPDATES)) {
//                clients.add(out);
//            }
//            if (request.getRequestType().equals(RequestType.GET_CONCERTS_BY_ARTIST)) {
//                Artist artist = (Artist) request.getBody();
//                System.out.println("Sending response");
//                out.writeObject(new Response(concertService.getConcertsByArtist(artist)));
//                out.flush();
//            }
//            if (request.getRequestType().equals(RequestType.GET_CONCERTS_BY_DATE)) {
//                String date = (String) request.getBody();
//                System.out.println("Sending response");
//                out.writeObject(new Response(concertService.getConcertsByDate(date)));
//                out.flush();
//            }
//            if (request.getRequestType().equals(RequestType.GET_CONCERTS_BY_ARTIST_AND_DATE)) {
//                WrapperArtistAndDate wrapperArtistAndDate = (WrapperArtistAndDate) request.getBody();
//                Artist artist = wrapperArtistAndDate.getArtist();
//                String date = wrapperArtistAndDate.getDate();
//                System.out.println("Sending response");
//                out.writeObject(new Response(concertService.getConcertsByArtistAndDate(artist, date)));
//                out.flush();
//            }
//            if (request.getRequestType().equals(RequestType.SAVE_TICKET)) {
//                Ticket ticket = (Ticket) request.getBody();
//                System.out.println("Sending response");
//                ticketService.save(ticket);
//                out.writeObject(new Response(null));
//                out.flush();
//            }
//            if (request.getRequestType().equals(RequestType.UPDATE_CONCERT)) {
//                Concert concert = (Concert) request.getBody();
//                System.out.println("Sending response");
//                concertService.put(concert);
//                out.writeObject(new Response(null));
//                out.flush();
//                System.out.println("Sending updates");
//
//
//                //########## UPDATEING OTHER CLIENTS
//                for (ObjectOutputStream cl : clients) {
//                    System.out.println("Sending update to:"+ cl);
//                    cl.flush();
//                    cl.writeObject(new Response(concert));
//                    cl.flush();
//                }
//            }
//            if (request.getRequestType().equals(RequestType.GET_ARTISTS)) {
//                System.out.println("Sending response");
//                out.writeObject(new Response(artistService.getAll()));
//                out.flush();
//            }
//            if (request.getRequestType().equals(RequestType.GET_CONCERTS)) {
//                System.out.println("Sending response");
//                out.writeObject(new Response(concertService.getAll()));
//                out.flush();
//            }
//            if (request.getRequestType().equals(RequestType.GET_LOCATIONS)) {
//                System.out.println("Sending response");
//                out.writeObject(new Response(locationService.getAll()));
//                out.flush();
//            }
    }
}
